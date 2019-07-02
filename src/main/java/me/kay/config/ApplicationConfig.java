package me.kay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ApplicationConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }

    @Bean
    @Conditional(RpcEnabledCondition.class)
    @SuppressWarnings({"unchecked", "deprecation"})
    public static com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceExporter autoJsonRpcServiceImplExporter(){
        com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceExporter jsonRpc = new com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceExporter();

        jsonRpc.setErrorResolver(new Web3jSafeAnnotationsErrorResolver());

        return jsonRpc;
    }
}
