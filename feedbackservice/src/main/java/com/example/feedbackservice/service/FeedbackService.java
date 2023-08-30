package com.example.feedbackservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.feedbackservice.model.PatientQuery;
import com.example.feedbackservice.modeldto.PatientQueryDto;
import com.example.feedbackservice.repo.FeedbackRepo;


@Service
public class FeedbackService {
	
	@Autowired
	FeedbackRepo feedbackRepo;

	public String addQuery(PatientQueryDto patientQueryDto, int patientId) {
		String response=null;
		PatientQuery patientQuery = new PatientQuery(patientQueryDto.getQuery(),patientId);
		patientQuery.setCreatedOn(LocalDateTime.now());
		feedbackRepo.save(patientQuery);
		response = "Query added";
		return response;
	}

	public PatientQuery getQuery(int queryId) {
		return feedbackRepo.findById(queryId).get();
	}

	public List<PatientQuery> getAllQuery() {
		return feedbackRepo.findAll();
	}

	public String deleteQuery(int queryId) {
		String response;

		try {
			feedbackRepo.deleteById(queryId);
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

	public String deleteQueryByPatientId(int patientID) {
		String response;

		try {
			feedbackRepo.deleteByPatientId(patientID);
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

	public String editUser(PatientQueryDto patientQueryDto, int queryId) {
		String response=null;
		try {
			Optional<PatientQuery> update = feedbackRepo.findById(queryId);
			PatientQuery result = update.get();
			result.setQuery(patientQueryDto.getQuery());
			if(feedbackRepo.save(result)!=null) {
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

	public List<PatientQuery> getQueryByPatientId(int patientId) {
		return feedbackRepo.findByPatientId(patientId);
	}

}
