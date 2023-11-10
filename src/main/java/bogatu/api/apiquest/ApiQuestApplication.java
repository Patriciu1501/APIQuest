package bogatu.api.apiquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableCaching
@EnableWebSecurity(debug = true)
public class ApiQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiQuestApplication.class, args);
    }

}
