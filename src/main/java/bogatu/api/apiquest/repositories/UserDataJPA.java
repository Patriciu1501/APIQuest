package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public non-sealed class UserDataJPA implements UserDAO{

    private final UserRepoDataJPA repo;


    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return repo.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repo.findUserByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        return repo.save(user);
    }

    @Override
    public void deleteUser(int id) {
        repo.deleteById(id);
    }

    @Override
    public void updateUser(User customer) {
        repo.save(customer);
    }
}
