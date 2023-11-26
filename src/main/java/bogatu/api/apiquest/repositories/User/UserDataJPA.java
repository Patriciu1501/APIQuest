package bogatu.api.apiquest.repositories.User;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserInfoProjection;
import bogatu.api.apiquest.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public non-sealed class UserDataJPA implements UserDAO{

    private final UserRepoDataJPA repo;


    @Override
    public List<User> getAllUsers(int pageNumber, int pageSize) {
        return repo.findAll(Pageable.ofSize(pageSize).withPage(pageNumber)).toList();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return repo.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repo.findByEmail(email);
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
    public void increaseScore(String email, int toAdd){
        repo.increaseScore(email, toAdd);
    }
}
