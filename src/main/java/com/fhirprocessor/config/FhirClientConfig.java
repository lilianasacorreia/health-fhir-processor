package com.fhirprocessor.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirClientConfig {

    @Bean
    public FhirContext fhirContext(){
        return FhirContext.forR5();
    }

    @Bean
    public IGenericClient fhirClient(FhirContext fhirCtx,
                                     @Value("${fhir.server.url}") String serverUrl){
        return fhirCtx.newRestfulGenericClient(serverUrl);
    }
}
