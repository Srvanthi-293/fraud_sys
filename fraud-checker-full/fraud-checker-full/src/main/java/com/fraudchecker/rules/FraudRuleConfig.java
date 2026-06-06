package com.fraudchecker.rules;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates a single KieContainer bean at startup.
 * Spring auto-discovers META-INF/kmodule.xml from the classpath,
 * which tells Drools to compile rules/fraud-rules.drl.
 */
@Configuration
public class FraudRuleConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        return kieServices.getKieClasspathContainer();
    }
}
