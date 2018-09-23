package org.mycontrib.spectacle.config;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"org.mycontrib.spectacle.dao"})
@EntityScan(basePackages= {"org.mycontrib.generic.security.persistence.entity" ,"org.mycontrib.spectacle.entity"})
@ComponentScan(basePackages={"org.mycontrib.spectacle","org.mycontrib.generic.security"})//with excludeFilters if needed
//@Import(MySwaggerConfig.class)
public class WithAutoConfiguration {
   //la configuration automatique tient compte des éléments
   //présents dans le classpath (selon pom.xml)
   //et du fichier application.properties	
}