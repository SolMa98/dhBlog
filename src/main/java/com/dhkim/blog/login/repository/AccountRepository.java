package com.dhkim.blog.login.repository;

import com.dhkim.blog.login.domain.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findById(String id);
    Optional<Account> findByNickname(String nickname);
}
