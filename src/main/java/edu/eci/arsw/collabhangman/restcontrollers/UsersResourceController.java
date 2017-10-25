/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.restcontrollers;

import edu.eci.arsw.collabhangman.persistence.PersistenceException;
import edu.eci.arsw.collabhangman.persistence.UsersRepository;
import edu.eci.arsw.collabhangman.services.GameServices;
import edu.eci.arsw.collabhangman.services.GameServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping("/users")
public class UsersResourceController {
  
    
    @Autowired
    UsersRepository Users;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(Users.getAllUsers(),HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(path = "/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable Integer userid){
        try{
            return new ResponseEntity<>(Users.getUserByID(userid),HttpStatus.ACCEPTED);
        }catch(PersistenceException ex){
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    
    public UsersRepository getUsers() {
        return Users;
    }

    public void setUsers(UsersRepository Users) {
        this.Users = Users;
    }
    
    
    
    
    
}
