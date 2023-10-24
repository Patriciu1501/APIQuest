package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDataJPA implements UserDAO{
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean verifyUserEmailExists(String email) {
        return false;
    }

    @Override
    public boolean verifyUserIdExists(int id) {
        return false;
    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void updateUser(User customer) {

    }
}
