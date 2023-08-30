package com.hackathon.patientservice.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hackathon.patientservice.commonservice.ViewAdherence;
import com.hackathon.patientservice.commonservice.ViewMedication;
import com.hackathon.patientservice.commonservice.ViewQuery;
import com.hackathon.patientservice.model.Adherence;
import com.hackathon.patientservice.model.Medication;
import com.hackathon.patientservice.model.Patient;
import com.hackathon.patientservice.model.PatientQuery;
import com.hackathon.patientservice.modeldto.PatientDto;
import com.hackathon.patientservice.repo.PatientRepo;

@Service
@Transactional
public class PatientService {

	@Autowired
	PatientRepo patientRepo;
	
	@Autowired
	ViewMedication viewMedication;
	
	@Autowired
	ViewAdherence viewAdherence;

	// @Autowired
	// ViewFeedback viewFeedback;
	
	
	@Autowired
	ViewQuery viewQuery; 
	
	public String addPatient(PatientDto patientDto, int userId) {
		String response=null;
		Patient patient = new Patient(patientDto.getMedicalCondition(), userId);
		patientRepo.save(patient);
		response = "Patient added";
		return response;
		
	}

	public Patient getPatient(int patientId) {
		Patient patient = patientRepo.findById(patientId).get();
		List<Medication> medication = viewMedication.getMedicationByPatientId(patientId);
		
		List<Medication> medicationfinal = medication.stream().map(med -> {
			List<Adherence> adherence = viewAdherence.getAdherenceByMedicationId(med.getMedicationId());
			med.setAdherence(adherence);
			return med;
		}).collect(Collectors.toList());
		
		List<PatientQuery> patientQueries = viewQuery.getQueryByPatientId(patientId);
		
		patient.setMedications(medicationfinal);
		patient.setPatientQueries(patientQueries);
		return patient;
	}
	
	
	public String deletePatient(int patientId) {
		String response;



		try {
			
			viewMedication.deleteMedicationByPatientId(patientId);
			viewQuery.deleteQueryByPatientId(patientId);
			patientRepo.deleteById(patientId);

			response = "Successfully Deleted";
		}
		catch(IllegalArgumentException e) {
			response = "Not Deleted , Please try again";
		}
		catch (EmptyResultDataAccessException e) {
			response = "No User Present with this User Id";
		}
		catch(Exception e) {
			response =  "Something Wrong";
		}
		return response;
	}

	public String deletePatientByUserId(int userId) {
		String response;

		try {
			
			Patient patient=patientRepo.findByUserId(userId).get();
			viewMedication.deleteMedicationByPatientId(patient.getPatientId());
			viewQuery.deleteQueryByPatientId(patient.getPatientId());
			patientRepo.deleteByUserId(userId);

			response = "Successfully Deleted";
		}
		catch(IllegalArgumentException e) {
			response = "Not Deleted , Please try again";
		}
		catch (EmptyResultDataAccessException e) {
			response = "No User Present with this User Id";
		}
		catch(Exception e) {
			response =  "Something Wrong";
		}
		return response;
	}



	public String editUser(PatientDto patientDto, int patientId) {
		String response=null;
		try {
			Optional<Patient> update = patientRepo.findById(patientId);
			Patient result = update.get();
			result.setMedicalCondition(patientDto.getMedicalCondition());
			if(patientRepo.save(result)!=null) {
				response = "Successfully Updated";
			}
			else {
				response = "Not Updated , Please try again";
			}
		}
		catch(NoSuchElementException e) {
			response = "Not Updated, userId is not present";
		}
		catch(Exception e) {
			response = "Something Wrong";
		}
		return response;
	}

	public  Optional<Patient> getPatientByUserId(int userId) {
		 Patient patient = patientRepo.findByUserId(userId).get();
		 int patientId = patient.getPatientId();
		 Optional<Patient> patient2 = Optional.of(getPatient(patientId));
		 return patient2;
	}
	
	
}