package com.example.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class IntercionalizacaoConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messsageSource = new ReloadableResourceBundleMessageSource();
        messsageSource.setBasename("classpath:messages");
        messsageSource.setDefaultEncoding("ISO-8859-1");
        messsageSource.setDefaultLocale(Locale.getDefault());
        return messsageSource;
    }
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
