package com.example.medicationservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.medicationservice.commonservice.ViewAdherence;
import com.example.medicationservice.model.Adherence;
import com.example.medicationservice.model.Medication;
import com.example.medicationservice.modeldto.MedicationDto;
import com.example.medicationservice.modeldto.ResponseDto;
import com.example.medicationservice.repo.MedicationRepo;


@Service
@Transactional
public class MedicationService {

	
	@Autowired
	MedicationRepo medicationRepo;
	
	@Autowired
	ViewAdherence viewAdherence;
	
	public String addmedication(MedicationDto medicationDto, int patientId) {
		String response=null;
		Medication medication = new Medication(medicationDto.getMedicationName(),medicationDto.getDosage(),medicationDto.getFrequency(),
				medicationDto.getStartDate(),medicationDto.getEndDate(),patientId);
		medication.setCreatedOn(LocalDateTime.now());
		medicationRepo.save(medication);
		response = "medication added";
		return response;
	}

	public String deletemedication(int medicationId) {
		String response;

		try {
			medicationRepo.deleteById(medicationId);
			viewAdherence.deleteAdherenceByMedicationId(medicationId);

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

	public String deleteMedicationByPatientId(int patientId) {
		String response;

		try {
			
			List<Medication> medication=medicationRepo.findByPatientId(patientId);
			for(Medication med : medication) {
				viewAdherence.deleteAdherenceByMedicationId(med.getMedicationId());
			}
			
			medicationRepo.deleteByPatientId(patientId);
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

	public String editmedication(MedicationDto medicationDto, int medicationId) {
		String response=null;
		try {
			Optional<Medication> update = medicationRepo.findById(medicationId);
			Medication result = update.get();
			result.setMedicationName(medicationDto.getMedicationName());
			result.setDosage(medicationDto.getDosage());
			result.setFrequency(medicationDto.getFrequency());
			result.setStartDate(medicationDto.getStartDate());
			result.setEndDate(medicationDto.getEndDate());
			if(medicationRepo.save(result)!=null) {
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

	public List<Medication> getMedicationByPatientId(int patientId) {
		return medicationRepo.findByPatientId(patientId);
	}
	
	public Medication getmedication(int medicationId) {
		List<Adherence> adherence = viewAdherence.getAdherenceByMedicationId(medicationId);

		Medication medication =  medicationRepo.findById(medicationId).get();
		
		medication.setAdherence(adherence);
		
		return medication;
	}

	public List<Medication> getReport(ResponseDto responseDto, int patientId) {
		List<Medication> medication =  medicationRepo.findAllByPatientIdAndCreatedOnBetween(patientId,
				responseDto.getStartDate(),responseDto.getEndDate());
		List<Medication> medicationfinal = medication.stream().map(med -> {
			List<Adherence> adherence = viewAdherence.getAdherenceByMedicationId(med.getMedicationId());
			med.setAdherence(adherence);
			return med;
		}).collect(Collectors.toList());
		return medicationfinal;
		
	}

}
