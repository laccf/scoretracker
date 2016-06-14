package br.com.itengine.score.repository;

import br.com.itengine.score.entity.Role;
import br.com.itengine.score.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by thiag.
 */
public interface UserRepository extends CrudRepository<User,Integer> {
    List<User> findAll();
    User findById(Integer id);
    List<User> findByRole(String role);
    User findByUsername(String username);
    List<User> findByUsernameNotContainingIgnoreCase(String username);
}
