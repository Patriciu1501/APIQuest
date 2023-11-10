package bogatu.api.apiquest;

import bogatu.api.apiquest.dtos.User.UserUpdateDTO;
import bogatu.api.apiquest.entities.User;
import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.UUID;

@Testcontainers
public abstract class TestContainersTest {

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("apiquest-test")
                    .withUsername("patriciu")
                    .withPassword("bogatu");



    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(),
                        postgreSQLContainer.getPassword()
                ).load();

        flyway.migrate();
    }

    @DynamicPropertySource
    private static void mapContainerDB(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @AfterAll
    static void afterAll(){
        postgreSQLContainer.close();
    }
}
