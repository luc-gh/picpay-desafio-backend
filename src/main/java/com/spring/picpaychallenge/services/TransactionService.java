package com.spring.picpaychallenge.services;

import com.spring.picpaychallenge.dto.TransactionDTO;
import com.spring.picpaychallenge.entities.Transaction;
import com.spring.picpaychallenge.entities.User;
import com.spring.picpaychallenge.repositories.TransactionRepository;
import lombok.Getter;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@Getter
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;  //Para requisições HTTP

    @Autowired
    private NotificationService notificationService;

    private static final String mock = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";

    //A função a seguir recebe o objeto que guarda os dados do payload (JSON) a partir de uma requisição POST
    public Transaction createTransaction(TransactionDTO dto) throws Exception {
        User sender = this.userService.findUserById(dto.senderId());
        User receiver = this.userService.findUserById(dto.receiverId());

        userService.validateTransaction(sender, dto.value());

        if (!this.authorizeTransaction(sender, dto.value())) throw new TransactionException("Transação não autorizada.");

        //Gerando nova transação
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(dto.value());
        transaction.setTimestamp(LocalDateTime.now());

        //Atualizando saldo do remetente
        sender.setBalance(sender.getBalance().subtract(dto.value()));

        //Atualizando saldo do destinatário
        receiver.setBalance(receiver.getBalance().add(dto.value()));

        //Persistindo dados no DB
        this.repository.save(transaction);
        userService.saveNewUser(sender);
        userService.saveNewUser(receiver);

        //Notificações
        this.notificationService.sendNotification(sender, "Transferência realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Valor recebido com sucesso!");

        return transaction;
    }

    private boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> response = this.restTemplate.getForEntity(mock, Map.class);
        if (response.getStatusCode() == HttpStatus.OK){
            String message = (String) response.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
