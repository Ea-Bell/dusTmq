package com.example.dusTmq.repository.oauth;

import com.example.dusTmq.domain.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}