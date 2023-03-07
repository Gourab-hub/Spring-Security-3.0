package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.UserEntity;

public interface UserInfoRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByName(String username);

}
