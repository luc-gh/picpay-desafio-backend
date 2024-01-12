package com.spring.picpaychallenge.config;

import com.spring.picpaychallenge.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.SystemException;
import org.hibernate.TransactionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice  //Anotação do Spring para um componente controlador de exceções global
public class GeneralExceptionHandler {

    //Duplicação de usuários
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatDuplicatedEntry(DataIntegrityViolationException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", 400, e.getMessage().substring(0,20));
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    //Usuário não encontrado
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> userNotFound(EntityNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    //Transação não autorizada
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ExceptionDTO> unauthorizedTransaction(TransactionException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), 401, e.getCause().toString().substring(0,20));
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    //Se lojistas tentarem realizar transações
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDTO> invalidUserType(IllegalArgumentException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), 401, Objects.requireNonNull(e.getCause().toString().substring(0, 20)));
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    //Exceções gerais
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> generalExceptionHandler(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), 500, Objects.requireNonNull(e.getCause().toString().substring(0, 20)));
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
