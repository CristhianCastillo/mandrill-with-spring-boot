package com.ptesa.demoemailmandril.config;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry(proxyTargetClass=true)
public class MandrillConfig {

    @Value("${mandrill.api.key}")
    String mandrillApiKey;

    @Bean
    public MandrillApi createMandrillApi() {
        return new MandrillApi(mandrillApiKey);
    }
}