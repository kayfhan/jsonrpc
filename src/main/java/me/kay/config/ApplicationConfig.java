package me.kay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    @Conditional(RpcEnabledCondition.class)
    @SuppressWarnings({"unchecked", "deprecation"})
    public static com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceExporter autoJsonRpcServiceImplExporter(){
        com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceExporter jsonRpc = new com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceExporter();

        jsonRpc.setErrorResolver(new Web3jSafeAnnotationsErrorResolver());

        return jsonRpc;
    }

}
