package me.kay.config;

import me.kay.service.ClientMessageService;
import me.kay.service.impl.ClientMessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {

    @Bean
    public ClientMessageService clientMessageService(){
        return new ClientMessageServiceImpl();
    }
}
