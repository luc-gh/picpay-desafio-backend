package com.spring.picpaychallenge.services;

import com.spring.picpaychallenge.dto.NotificationDTO;
import com.spring.picpaychallenge.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String sendMock = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

    public void sendNotification(User user, String message) throws Exception {
        NotificationDTO notificationData = new NotificationDTO(user.getEmail(), message);
        ResponseEntity<String> response = this.restTemplate.postForEntity(sendMock, notificationData, String.class);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Falha nas notificações");
            throw new Exception("Serviço de notificação fora do ar.");
        }

    }
}
