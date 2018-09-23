package org.mycontrib.generic.security.generic;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/* classe java pour serialisation JSON 
 * Cette classe pourra servir à générer (via jackson) 
 * une chaine de caractère au format json comportant
 * des informations sur un utilisateur d'une application
 * 
 * Autrement dit , la chaine json construite comportera un tout petit peu 
 * d'informations redondantes sur un utilisateur qui seront
 * stockées dans la partie "detail" de LoginAccount
 */

@Getter @Setter  @NoArgsConstructor @ToString
public class LoginAccountDetails {
	
	private String email;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	
	private static Logger logger = LoggerFactory.getLogger(LoginAccountDetails.class);

	private static ObjectMapper mapper = new ObjectMapper();
	
	public LoginAccountDetails(String email, String phoneNumber, String firstName, String lastName) {
		super();
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String toJsonString(){
		String jsonString=null;
	   	try {
			jsonString =  mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	   	return jsonString;
	}

	public static LoginAccountDetails parseJsonString(String jsonString){
		LoginAccountDetails details = null;
		try {
			details = mapper.readValue(jsonString, LoginAccountDetails.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return details;
	}

}
