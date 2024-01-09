package com.spring.picpaychallenge.services;

import com.spring.picpaychallenge.entities.User;
import com.spring.picpaychallenge.repositories.UserRepository;
import com.spring.picpaychallenge.resources.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount){
        if (sender.getUserType().equals(UserType.MERCHANT)) {
            throw new RuntimeException("Transferência inválida. Tipo de usuário não autorizado.");
        }
        if (sender.getBalance().longValueExact() < amount.longValueExact()){
            throw new RuntimeException("Transferência não realizada. Usuário não tem saldo para tal.");
        }
        System.out.println("Transação realizada.");
    }

}
