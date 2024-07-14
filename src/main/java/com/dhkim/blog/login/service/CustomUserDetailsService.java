package com.dhkim.blog.login.service;

import com.dhkim.blog.login.domain.Account;
import com.dhkim.blog.login.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findById(username)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Account account){
        return User.builder()
                .username(account.getId())
                .password(account.getPassword())
                .roles(account.getRoles().toArray(new String[0]))
                .build();
    }
}
