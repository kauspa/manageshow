package com.demo.manageshow;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.demo.manageshow")
@PropertySource("classpath:application.properties")
public class ManageShowClientConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }
}
