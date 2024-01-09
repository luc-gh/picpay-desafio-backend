package com.spring.picpaychallenge.repositories;

import com.spring.picpaychallenge.entities.User;
import com.spring.picpaychallenge.resources.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

//JPARepository needs the datatype of its table, that will be manipulated, and its id type
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);
    Optional<User> findUserById(Long id);
}
