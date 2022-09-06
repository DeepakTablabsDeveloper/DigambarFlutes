package com.ecom.core.security;
/*
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ecom.core.repository.CustomersRepository;



@Configuration
@EnableWebSecurity
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	CustomersRepository CustomersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        //Customers companyRegistration = CustomersRepository.findByMobileNumberAndPassword(email, password);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Admin")); // description is a string

        try{
        return new UsernamePasswordAuthenticationToken(email, password, authorities);
        }catch(Exception e){
        	throw new AuthenticationCredentialsNotFoundException("Enter valid credentials to access this API ");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}*/