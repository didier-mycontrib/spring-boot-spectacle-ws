package org.mycontrib.spectacle.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("web.dev")
@ComponentScan("org.mycontrib.s.web.dev.only")
public class WebDevOnlyConfig {

}
