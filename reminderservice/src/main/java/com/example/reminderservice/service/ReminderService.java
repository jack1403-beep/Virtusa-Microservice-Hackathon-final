package com.example.reminderservice.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.reminderservice.commonservice.ViewAdherence;
import com.example.reminderservice.commonservice.ViewMedication;
import com.example.reminderservice.commonservice.ViewUser;
import com.example.reminderservice.modeldto.Adherence;
import com.example.reminderservice.modeldto.Mail;
import com.example.reminderservice.modeldto.Medication;
import com.example.reminderservice.modeldto.User;


@Service
public class ReminderService {

	@Autowired
	ViewAdherence viewAdherence;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	ViewUser viewUser; 
	
	@Autowired
	ViewMedication viewMedication;
	
	
	@Scheduled(fixedRate = 5000)
	public void sendNotification() {
		
		
		List<Adherence> adherences = viewAdherence.getMail();
		Mail mail = new Mail();
		for(Adherence data : adherences) {
			LocalTime mailTime = data.getAdherenceTime();
			boolean flag = data.isMailSent();
			if(mailTime.isBefore(LocalTime.now())&& !flag) {
				
				User user= viewUser.getUser(data.getUserId());
				Medication medication = viewMedication.getmedication(data.getMedicationId());
				mail.setMailFrom("gnagaikwad@gmail.com");

				mail.setMailTo(user.getEmail());
				mail.setMailSubject("Adherence Notification");

				String content = "Hi [[name]],<br><br>"
						+ "This is a friendly to take your medication as prescribed. Your health is important, and taking your medication on time "
						+ "is crucial for your well-being.<br><br>"
						+ "Medication Details: <br>"
						+ "- Medication Name: [[Medication Name]] <br>"
						+ "- Dosage: [[Dosage]] <br>"
						+ "- Frequency: [[Frequency]] <br><br>"
						+ "Please ensure that you follow your healthcare provider's instructions and take your medication as scheduled.If you have any quentions or concerns"
						+ "about uour medication, don't hesitate to reach out to your healthcare provider.<br>"
						+ "Remember, your health is a priority, and staying on top your medication regimen is a positive step towards maintaining your well-being.<br>"
						+ "Thank you for taking care of yourself!<br>";
						
				
				
				content = content.replace("[[name]]",user.getFirstName());
				content = content.replace("[[Medication Name]]",medication.getMedicationName());
				content = content.replace(" [[Dosage]]",medication.getDosage());
				content = content.replace("[[Frequency]]",medication.getFrequency());

				mail.setMailContent(content);

				mailService.sendEmail(mail);
				viewAdherence.setMailToTrue(data.getAdherenceId());
			}
			
		}
		
	}
}
