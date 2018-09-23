package org.mycontrib.generic.security.rest.sping.mvc;

import org.mycontrib.generic.security.generic.LoginAccountDetails;
import org.mycontrib.generic.security.persistence.entity.LoginAccount;
import org.mycontrib.generic.security.rest.payload.AuthRequest;
import org.mycontrib.generic.security.rest.payload.AuthResponse;
import org.mycontrib.generic.security.rest.payload.NewUser;
import org.mycontrib.generic.security.rest.payload.RegisterUserResponse;
import org.mycontrib.generic.security.service.LoginAccountService;
import org.mycontrib.generic.security.spring.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// sign up = subscribe/register = s'inscrire

// sign in = login = se connecter

@RestController
@RequestMapping(value="/auth" , 
                headers="Accept=application/json")
public class AuthController /*extends AbstractRestAuthWS*/ {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	

	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private LoginAccountService defaultUserAccountService;
	
	@Autowired(required=false)
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {    	
    	logger.debug("/login , loginRequest:"+loginRequest);
    	
    	//NB: authenticationManager is built/configure in GenericWebSecurityConfig
    	//with AuthenticationManagerBuilder and UserAccountDetailsService
        Authentication authentication = null;
        AuthResponse authResponse = new AuthResponse();
        try {
			authentication=authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			                loginRequest.getUsername(),
			                loginRequest.getPassword()
			        )
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.generateToken(authentication);
	        authResponse.setAuthToken(jwt);
	        authResponse.setAuthOk(true);
	        authResponse.setMessage("login successful");
	
	        logger.debug("/login authResponse:" + authResponse.toString());
	        return ResponseEntity.ok(authResponse);
		} catch (AuthenticationException e) {
			logger.debug("echec authentification:" + e.getMessage()); //for log
			 authResponse.setAuthOk(false);
		     authResponse.setMessage("echec authentification");
		     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		    		              .body(authResponse);
		    		            
		}
        
    }

    //NB:  /auth/registerUser et registerUser(newUser)
    //     est ici proposé en tant qu'implémentation par défaut 
    //     un peu testée et peaufinable / personnalisable
    //     DANS LA PLUPART DES CAS L'application aura besoin
    //     d'une version ( parallèle ou bien de remplacement)
    //     QUI SERA SPECIFIQUEMENT ADAPTEE AU BESOIN DE L'APPLICATION .
    
    @PostMapping("/registerUser")
    public ResponseEntity<RegisterUserResponse> registerUser(/*@Valid*/ @RequestBody NewUser newUser) {
    	
    	logger.debug("registerUser() called with newUser="+newUser.toString());
    	
        if(!defaultUserAccountService.checkUserNameAvailability(newUser.getUsername(),LoginAccountService.DEFAULT_FULL_CONTEXT_NAME)) {
            return new ResponseEntity<RegisterUserResponse>(
            		      new RegisterUserResponse(false, "(unique) Username is already taken!",newUser),
                          HttpStatus.BAD_REQUEST);
        }

        if(newUser.getEmail()!=null && defaultUserAccountService.alreadyExistsWithEmail(newUser.getEmail(),LoginAccountService.DEFAULT_FULL_CONTEXT_NAME)) {
            return new ResponseEntity<RegisterUserResponse>(
            		      new RegisterUserResponse(false, "(unique) Email Address already in use!",newUser),
            		      HttpStatus.BAD_REQUEST);
        }

        String passwordToStoreInDB=null;
        if(passwordEncoder !=null){
    		String bcryptedPassword = passwordEncoder.encode(newUser.getPassword());
    		logger.debug("/registerUser , bcryptedPassword:"+bcryptedPassword);
    		passwordToStoreInDB=bcryptedPassword;
    	}else {
    		passwordToStoreInDB=newUser.getPassword();
    	}
        
        // Creating user's account
        LoginAccount userAccount = new LoginAccount(newUser.getUsername(),passwordToStoreInDB);
        
        if(newUser.getEmail()!=null || newUser.getPhoneNumber()!=null
        	|| newUser.getFirstName()!=null || newUser.getLastName()!=null){
        	LoginAccountDetails userAccountDetails = 
        			new LoginAccountDetails(newUser.getEmail(),newUser.getPhoneNumber(),
        			                       newUser.getFirstName(),newUser.getLastName());
        	userAccount.setDetail(userAccountDetails.toJsonString());
        }
        
        userAccount = defaultUserAccountService.createAccountInGroup(userAccount,
        		                      LoginAccountService.DEFAULT_FULL_CONTEXT_NAME);

        /*
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();

        return ResponseEntity<RegisterUserResponse>
               .created(location)
               .body(new RegisterUserResponse(true, "User registered successfully",
                                              newUser,userAccount.getUserId()));
        */
        return new ResponseEntity<RegisterUserResponse>(new RegisterUserResponse(true, 
        		                      "User registered successfully",
        		                      newUser,
        		                      userAccount.getLoginId()),
                   HttpStatus.OK);
    }
}


