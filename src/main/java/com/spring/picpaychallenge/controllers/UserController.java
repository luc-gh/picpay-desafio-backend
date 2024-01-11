package com.spring.picpaychallenge.controllers;

import com.spring.picpaychallenge.dto.UserDTO;
import com.spring.picpaychallenge.entities.User;
import com.spring.picpaychallenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping  //Endpoint de criação de usuários
    public ResponseEntity<User> createUser(@RequestBody UserDTO userData){  //RequestBody indica que esses são os dados da req
        User user = userService.saveNewUser(userData);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping   //Endpoint de listagem de usuários (verificação de funcionamento)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
