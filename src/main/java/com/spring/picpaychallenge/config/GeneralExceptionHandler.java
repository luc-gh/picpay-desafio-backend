package com.spring.picpaychallenge.config;

import com.spring.picpaychallenge.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.TransactionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //Anotação do Spring para um componente controlador de exceções global
public class GeneralExceptionHandler {

    //Duplicação de usuários
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatDuplicatedEntry(DataIntegrityViolationException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", 400);
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> userNotFound(EntityNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    //Transação não autorizada
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ExceptionDTO> unauthorizedTransaction(TransactionException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), 401);
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    //Exceções gerais
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> generalExceptionHandler(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), 500);
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
