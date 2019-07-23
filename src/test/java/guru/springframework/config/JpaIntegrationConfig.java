package guru.springframework.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("guru.springframework")
public class JpaIntegrationConfig {

    @Bean
    public StrongPasswordEncryptor strongPasswordEncryptor() {
        return new StrongPasswordEncryptor();
    }
}
