package com.ecom.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class Validations {

	 public  boolean mobileNumberValidation(String s) 
	    { 
	        // The given argument to compile() method  
	        // is regular expression. With the help of  
	        // regular expression we can validate mobile 
	        // number.  
	        // 1) Begins with 0 or 91 
	        // 2) Then contains 7 or 8 or 9. 
	        // 3) Then contains 9 digits 
	        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}"); 
	  
	        // Pattern class contains matcher() method 
	        // to find matching between given number  
	        // and regular expression 
	        Matcher m = p.matcher(s); 
	        return (m.find() && m.group().equals(s)); 
	    } 
	 
	 public  boolean emailIdValidation(String email) 
	    { 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    } 
	 
}
