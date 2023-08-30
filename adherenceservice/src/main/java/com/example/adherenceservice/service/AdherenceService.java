package com.example.adherenceservice.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.adherenceservice.model.Adherence;
import com.example.adherenceservice.modeldto.AdherenceDto;
import com.example.adherenceservice.repo.AdherenceRepo;

@Service
@Transactional
public class AdherenceService {

	
	@Autowired
	AdherenceRepo adherenceRepo;
	
	public String addadherence(AdherenceDto adherenceDto, int medicationId) {
		String response=null;
	
		Adherence adherence = new Adherence(adherenceDto.getAdherenceTime(),medicationId,adherenceDto.getUserId());
		adherence.setMedicineTaken(false);
		adherence.setMailSent(false);
		adherenceRepo.save(adherence);
		response = "Remainder added";
		return response;
	}

	public Optional<Adherence> getadherence(int adherenceId) {
		return adherenceRepo.findById(adherenceId);
	}

	public String deleteadherence(int adherenceId) {
		String response;

		try {
			adherenceRepo.deleteById(adherenceId);
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

	public String deleteAdherenceByUserId(int userId) {
		String response;

		try {
			adherenceRepo.deleteByUserId(userId);
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

	public String deleteAdherenceByMedicationId(int medicationId) {
		String response;

		try {
			adherenceRepo.deleteByMedicationId(medicationId);
			
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

	public String editadherence(AdherenceDto adherenceDto, int adherenceId) {
		String response=null;
		try {
			Optional<Adherence> update = adherenceRepo.findById(adherenceId);
			Adherence result = update.get();
			result.setAdherenceTime(adherenceDto.getAdherenceTime());
			result.setMedicineTaken(adherenceDto.isMedicineTaken());
			if(adherenceRepo.save(result)!=null) {
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

	public List<Adherence> getAdherenceByMedicationId(int medicationId) {
		return adherenceRepo.findByMedicationId(medicationId);
	}

	public List<Adherence> getMail() {
		return adherenceRepo.findAllByMedicineTaken(false);
	}

	public List<Adherence> getAdherenceByUserId(int userId) {
		return adherenceRepo.findByUserId(userId);
	}

	public void setMailToTrue(int adherenceId) {
		Optional<Adherence> update = adherenceRepo.findById(adherenceId);
		Adherence result = update.get();
		result.setMailSent(true);
		adherenceRepo.save(result);
		
	}
	
//	@Scheduled(cron = "0 0 12 * * ?")
	@Scheduled(cron = "0 0 0 * * ?")
	public void setFlagToFalse() {
		List<Adherence> adherences = adherenceRepo.findAll();
		for( Adherence data : adherences) {
			data.setMailSent(false);
			data.setMedicineTaken(false);
		}
	}
}
