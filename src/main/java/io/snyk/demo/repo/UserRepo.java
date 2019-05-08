package io.snyk.demo.repo;

import io.snyk.demo.domain.Message;
import io.snyk.demo.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
        User findByUsername(String username);
        User findByUsernameAndAndPassword(String username, String password);
        User findByToken(String token);

}
