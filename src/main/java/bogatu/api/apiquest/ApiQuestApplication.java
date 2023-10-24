package bogatu.api.apiquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiQuestApplication.class, args);
    }

}
