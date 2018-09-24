package org.mycontrib.spectacle.config.security;

import org.mycontrib.generic.security.spring.security.config.GenericWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends GenericWebSecurityConfig {
	
	protected void apiSpecificHttpConfig(HttpSecurity http)throws Exception {
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
	}

}
