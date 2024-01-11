package com.spring.picpaychallenge.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.jackson.JsonComponent;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {}
