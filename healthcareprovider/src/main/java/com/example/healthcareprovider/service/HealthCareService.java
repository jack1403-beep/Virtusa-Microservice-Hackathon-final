package com.example.healthcareprovider.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.healthcareprovider.model.HealthCareProvider;
import com.example.healthcareprovider.modeldto.HealthCareProviderDto;
import com.example.healthcareprovider.repo.HealthCareRepo;


@Service
@Transactional
public class HealthCareService {
	
	@Autowired
	HealthCareRepo careRepo;

	public String addHealthCareProvider(HealthCareProviderDto careProviderDto, int healthCareProviderId) {
		String response=null;
		HealthCareProvider careProvider = new HealthCareProvider(careProviderDto.getSpecialization(),
				careProviderDto.getLicenseNumber(), careProviderDto.getHospitalName(), healthCareProviderId);
		careRepo.save(careProvider);
		response = "Health Care Provider added";
		return response;
	}

	public Optional<HealthCareProvider> getHealthCareProvider(int healthCareProviderId) {
		return careRepo.findById(healthCareProviderId);
	}

	public String deleteHealthCareProvider(int healthCareProviderId) {
		String response;

		try {
			careRepo.deleteById(healthCareProviderId);
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

	public String deleteHealthCareProviderByUserId(int userId) {
		String response;

		try {
			careRepo.deleteByUserId(userId);
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

	public String editHealthCareProvider(HealthCareProviderDto careProviderDto, int healthCareProviderId) {
		String response=null;
		try {
			Optional<HealthCareProvider> update = careRepo.findById(healthCareProviderId);
			HealthCareProvider result = update.get();
			result.setHospitalName(careProviderDto.getHospitalName());
			result.setLicenseNumber(careProviderDto.getLicenseNumber());
			result.setSpecialization(careProviderDto.getSpecialization());
			if(careRepo.save(result)!=null) {
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

	public Optional<HealthCareProvider> getHealthCareProviderByUserId(int userId) {
		return careRepo.findByUserId(userId);
	}

}
