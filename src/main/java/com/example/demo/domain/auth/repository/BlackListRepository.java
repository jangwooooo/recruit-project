package com.example.demo.domain.auth.repository;


import com.example.demo.domain.auth.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList,String> {
}