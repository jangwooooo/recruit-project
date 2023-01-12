package com.example.auth.domain.email.repository;

import com.example.auth.domain.email.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth,String> {
}
