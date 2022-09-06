package com.ecom.core.util;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FireBaseNotifications {

	@Value("${FCMKey}")
	private String fcmKey;
	
	@Value("${FCMurl}")
	private String fcmUrl;
	
	
	public void callNotificationsService(String title,String message,String notyficationType,String deviceId){
		try {
			   String androidFcmKey=fcmKey;
			   String androidFcmUrl=fcmUrl;

			   RestTemplate restTemplate = new RestTemplate();
			   HttpHeaders httpHeaders = new HttpHeaders();
			   httpHeaders.set("Authorization", "key=" + androidFcmKey);
			   httpHeaders.set("Content-Type", "application/json");
			   JSONObject msg = new JSONObject();
			   JSONObject json = new JSONObject();
			   msg.put("title", title);
			   msg.put("body", message);
			   msg.put("notificationType", notyficationType);
			   json.put("data", msg);
			   json.put("to", deviceId);
			   HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(),httpHeaders);
			   String response = restTemplate.postForObject(androidFcmUrl,httpEntity,String.class);
			   System.out.println(response);
			} catch (JSONException e) {
			   e.printStackTrace();
			} catch (Exception e) {
			   e.printStackTrace();
			}
	}
	
	
}
