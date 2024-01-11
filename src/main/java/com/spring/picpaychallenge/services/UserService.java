package com.spring.picpaychallenge.services;

import com.spring.picpaychallenge.dto.UserDTO;
import com.spring.picpaychallenge.entities.User;
import com.spring.picpaychallenge.repositories.UserRepository;
import com.spring.picpaychallenge.resources.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Transferência inválida. Tipo de usuário não autorizado.");
        }
        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Transferência não realizada. Usuário não tem saldo para tal.");
        }
        System.out.println("Transação válida.");
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public void saveNewUser(User user){
        this.repository.save(user);
    }

    public User saveNewUser(UserDTO data){
        User user = new User();  //Auto-generated id
        user.setFirstName(data.firstName());
        user.setLastName(data.lastName());
        user.setBalance(data.balance());
        user.setEmail(data.email());
        user.setPassword(data.password());
        user.setDocument(data.document());
        user.setUserType(data.userType());
        System.out.println(user.getId());
        this.repository.save(user);
        return user;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }
}
