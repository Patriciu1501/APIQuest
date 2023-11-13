package bogatu.api.apiquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EnableWebSecurity(debug = true)
public class ApiQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiQuestApplication.class, args);
    }


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }
}
