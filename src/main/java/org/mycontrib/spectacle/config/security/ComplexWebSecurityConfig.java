package org.mycontrib.spectacle.config.security;

import org.mycontrib.generic.security.spring.security.config.GenericWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@Profile("complex-security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ComplexWebSecurityConfig extends GenericWebSecurityConfig {
	
	
	protected void apiSpecificHttpConfig(HttpSecurity http)throws Exception {
		            System.out.println("apiSpecificHttpConfig ...");
					// Security policy
					http.authorizeRequests()
						.antMatchers("/",
		                        "/favicon.ico",
		                        "/**/*.png",
		                        "/**/*.gif",
		                        "/**/*.svg",
		                        "/**/*.jpg",
		                        "/**/*.html",
		                        "/**/*.css",
		                        "/**/*.js")
		                        .permitAll()
		                    .antMatchers(HttpMethod.POST,"/auth/**")
		                        .permitAll()
		                    .antMatchers(HttpMethod.GET,"/spectacle-api/public/**")
		                        .permitAll()
						// Any other request must be authenticated
						.anyRequest().authenticated();
		                        
		                //temporairement pour swagger:
		                //        .antMatchers("/**/*.*").permitAll();
					
					//NB: pour swagger renseigner l'url complete suivante
					//au sein d'un navigateur:
					// http://localhost:8888/spring-boot-spectacle-ws/swagger-ui.html
					// et http://localhost:8888/spring-boot-spectacle-ws/v2/api-docs
	}

}
