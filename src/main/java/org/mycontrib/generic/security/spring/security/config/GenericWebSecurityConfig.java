package org.mycontrib.generic.security.spring.security.config;

import org.mycontrib.generic.security.spring.security.DefaultCustomSpringSecurityDetailsService;
import org.mycontrib.generic.security.spring.security.JwtAuthenticationEntryPoint;
import org.mycontrib.generic.security.spring.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration in "WebSecurityConfig" subclass specific to api / no generic
//@EnableWebSecurity in "WebSecurityConfig" subclass specific to api / no generic
//@EnableGlobalMethodSecurity(prePostEnabled = true) in "WebSecurityConfig" subclass specific to api / no generic
//NB: @PreAuthorize need the option prePostEnabled = true
public abstract class GenericWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	DefaultCustomSpringSecurityDetailsService customUserDetailsService;
	
	protected abstract void apiSpecificHttpConfig(HttpSecurity http)throws Exception;
	
	@Override
    @Bean  //necessary since spring-boot 2.x and spring 5.x
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// Disable CSRF protection since tokens are immune to it
			.csrf().disable()
			// If the user is not authenticated, returns 401
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			// This is a stateless application, disable sessions
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			// Custom filter for authenticating users using tokens
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			// Disable resource caching
			.headers().cacheControl();
		
		    apiSpecificHttpConfig(http); //should be code in "WebSecurityConfig" subclass
	}	
	
	@Override
	protected void configure(AuthenticationManagerBuilder authMgrBuilder) throws Exception {
		authMgrBuilder.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

}
