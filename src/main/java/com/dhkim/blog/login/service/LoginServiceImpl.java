package com.dhkim.blog.login.service;

import com.dhkim.blog.login.domain.Account;
import com.dhkim.blog.login.dto.AccountDto;
import com.dhkim.blog.login.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String loginPageOpen(HttpServletRequest request){
        return "/login/login";
    }

    @Override
    public String createAccountPageOpen(HttpServletRequest request){
        return "/login/accountCreate";
    }

    @Override
    public boolean isIdUnique(String id){
        Optional<Account> existingAccount = accountRepository.findById(id);
        return !existingAccount.isPresent();
    }

    @Override
    public boolean isNicknameUnique(String nickname){
        Optional<Account> existingAccount = accountRepository.findByNickname(nickname);
        return !existingAccount.isPresent();
    }

    @Override
    public ResponseEntity<String> saveAccount(HttpServletRequest request, AccountDto accountDto){
        try{
            Account account = new Account();
            account.setId(accountDto.getId());
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            account.setNickname(accountDto.getNickname());
            account.setUsername(accountDto.getName());
            account.setGender(accountDto.getGender());

            accountRepository.save(account);

            return ResponseEntity.ok("success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
        }
    }
}
