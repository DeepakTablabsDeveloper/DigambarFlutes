package com.ecom.core.schedular;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BirthdayNotificationSchedular {

	@Scheduled(cron = "0 0 7 ? * *")
	public void BirthdayNotification() {
		System.out.println(" Hello Email Send ");
	}
	
}
