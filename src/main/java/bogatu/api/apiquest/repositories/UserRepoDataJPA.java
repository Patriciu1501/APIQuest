package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepoDataJPA extends CrudRepository<User, Integer> {
}
