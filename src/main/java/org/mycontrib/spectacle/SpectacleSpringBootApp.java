package org.mycontrib.spectacle;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//NB: @SpringBootApplication est un equivalent 
//de @Configuration + @EnableAutoConfiguration + @ComponentScan/current package

@SpringBootApplication
// automatic @Import(AdditionalSpecificConfiguration.class) / @Configuration in sub package
// automatic @Import(WebSecurityConfig.class) / @Configuration in sub package
public class SpectacleSpringBootApp {
	// le boot complet avec webApp prise en charge par tomcat embarqué .
	
	public static void main(String[] args) {
			
		// on prépare la configuration de l'application en mode spring-boot
		
	     SpringApplication app = new SpringApplication(SpectacleSpringBootApp.class);
		
		//app.setLogStartupInfo(false);
		
		//setting profile (context.getEnvironment().setActiveProfiles("...") ) :
		//app.setAdditionalProfiles( "p1" , "with-thymeleaf"); 
		
		// on lance l'application spring
		ConfigurableApplicationContext context =  app.run(args);
		
		System.out.println("localhost:8888/spring-boot-spectacle-ws");
		
	}

}
