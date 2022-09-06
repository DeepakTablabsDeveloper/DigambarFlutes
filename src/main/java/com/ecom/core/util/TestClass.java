package com.ecom.core.util;

import java.util.HashMap;
import java.util.Map;

public class TestClass {

	public static void main(String args[]) {
		
		String name="My Name is Deepak Salve";
		
		String newName=name.toLowerCase();
		
		Map<String,Integer> counter=new HashMap<>();
		
		int charCounter=1;
		
		for(int i=0;i<newName.length()-1;i++) {
			
			if(counter.containsKey(newName.charAt(i)+"")) {
				
				int count=counter.get(newName.charAt(i)+"");
				counter.put(newName.charAt(i)+"", ++count);
			}else {
				counter.put(newName.charAt(i)+"", charCounter);
			}
		}
		
		for(Map.Entry<String, Integer> charaterSepeat:counter.entrySet())
		{
		    if(charaterSepeat.getValue()>1) {	
			System.out.println(" Charater "+charaterSepeat.getKey()+" Sepeated Time "+charaterSepeat.getValue());
			}	
		}
		
		
	}
	
}
