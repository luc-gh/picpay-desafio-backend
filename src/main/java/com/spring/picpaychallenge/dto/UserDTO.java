package com.spring.picpaychallenge.dto;

import com.spring.picpaychallenge.resources.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, UserType userType, BigDecimal balance, String email, String password) {}
