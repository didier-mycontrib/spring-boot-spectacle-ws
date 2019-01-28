package org.mycontrib.spectacle.config;



import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@EnableAutoConfiguration //already as a sub behavior of @SpringBootApplication in parent config
@EnableJpaRepositories(basePackages = {"org.mycontrib.spectacle.dao"}) //default is package (and subs) of @SpringBootApplication
@EntityScan(basePackages= {"org.mycontrib.generic.security.persistence.entity" ,
		                  "org.mycontrib.spectacle.entity"}) //default is package (and subs) of @SpringBootApplication

//@ComponentScan(basePackages={"org.mycontrib.spectacle","org.mycontrib.generic.security"})//with excludeFilters if needed
@ComponentScan(basePackages={"org.mycontrib.generic.security"}) // as additional package(s) to scan
// "org.mycontrib.spectacle" already default package (and subs) to scan as a sub behavior of @SpringBootApplication in parent config
//@Import(MySwaggerConfig.class)
public class AdditionalSpecificConfiguration {
   //la configuration automatique tient compte des éléments
   //présents dans le classpath (selon pom.xml)
   //et du fichier application.properties	
}