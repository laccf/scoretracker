package br.com.itengine.score.controller;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itengine.score.entity.Role;
import br.com.itengine.score.entity.User;
import br.com.itengine.score.repository.UserRepository;

/**
 * Created by thiag.
 */

@RestController
@RequestMapping("/rest/users")
@PreAuthorize("hasRole('ROLE_ROOT')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="",method = RequestMethod.GET)
    public ResponseEntity<List<User>> findAll(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return new ResponseEntity<List<User>>(userRepository.findByUsernameNotContainingIgnoreCase(user.getUsername()), HttpStatus.OK);
    }


    @RequestMapping(value="/currentuser",method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return new ResponseEntity<User>(userRepository.findByUsername(principal.getName()), HttpStatus.OK);
    }


    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<User>(userRepository.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value="/role",method = RequestMethod.GET)
    public ResponseEntity<List<User>> findByRole(@RequestParam String role) {
        return new ResponseEntity<List<User>>(userRepository.findByRole(role), HttpStatus.OK);
    }

    @RequestMapping(value="/roles",method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = new LinkedList<>();
        roles.add(Role.ROLE_ROOT);
        roles.add(Role.ROLE_DELEGATE);
        roles.add(Role.ROLE_LEAGUE);
        roles.add(Role.ROLE_TEAM);

        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }

    @RequestMapping(value="",method = RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody User user,Principal principal) {
        User userprincipal = userRepository.findByUsername(principal.getName());
        if(userprincipal.getUsername().equalsIgnoreCase(user.getName()) || userprincipal.getId() == user.getId() ){
            return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value="",method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(User user) {
        if(userRepository.exists(user.getId())){
            userRepository.delete(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable Integer id) {
        if(userRepository.exists(id)){
            User user = userRepository.findOne(id);
            userRepository.delete(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
        }
    }


}
