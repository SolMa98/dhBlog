package com.dhkim.blog.login.service;

import com.dhkim.blog.login.domain.Account;
import com.dhkim.blog.login.domain.JwtToken;
import com.dhkim.blog.login.domain.Token;
import com.dhkim.blog.login.dto.AccountDto;
import com.dhkim.blog.login.jwt.JwtTokenProvider;
import com.dhkim.blog.login.repository.AccountRepository;
import com.dhkim.blog.login.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService{

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginServiceImpl(AccountRepository accountRepository,
                            TokenRepository tokenRepository,
                            PasswordEncoder passwordEncoder,
                            AuthenticationManagerBuilder authenticationManagerBuilder,
                            JwtTokenProvider jwtTokenProvider){
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String loginPageOpen(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId != null && !userId.isEmpty()) {
            return "redirect:/post/list";
        }else{
            return "/login/login";
        }
    }

    @Override
    public String createAccountPageOpen(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId != null && !userId.isEmpty()) {
            return "redirect:/post/list";
        }else{
            return "/login/accountCreate";
        }
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
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            account.setRoles(roles);

            accountRepository.save(account);

            return ResponseEntity.ok("success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
        }
    }

    @Override
    public String signIn(HttpServletRequest request, String id, String password){
        try {
            // 토큰 검사
            if (tokenRepository.findById(id).isPresent()) {
                tokenRepository.deleteById(id);
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

            Token loginInfo = new Token();
            Optional<Account> accountOptional = accountRepository.findById(id);

            accountOptional.ifPresent(account -> {
                loginInfo.setId(account.getId());
                loginInfo.setNickname(account.getNickname());
            });

            loginInfo.setAccessToken(jwtToken.getAccessToken());
            loginInfo.setRefreshToken(jwtToken.getRefreshToken());

            HttpSession session = request.getSession();
            session.setAttribute("userId", id);
            session.setAttribute("userNickname", loginInfo.getNickname());
            session.setAttribute("accessToken", jwtToken.getAccessToken());

            tokenRepository.save(loginInfo);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
    }

    @Override
    public String logout(HttpServletRequest request){
        try{
            HttpSession session = request.getSession();
            Object userIdObj = session.getAttribute("userId");
            tokenRepository.deleteById(userIdObj.toString());

            session.removeAttribute("userId");
            session.removeAttribute("userNickname");
            session.removeAttribute("accessToken");
            return "success";
        }catch (Exception e){
            return "failed";
        }
    }

    @Override
    public Boolean jwtTokenValidation(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");
        Object userAccessToken = session.getAttribute("accessToken");

        if (userIdObj != null) {
            if(!jwtTokenProvider.validateToken(userAccessToken.toString())) {
                Optional<Token> tokenOptional = tokenRepository.findById(userIdObj.toString());
                if(tokenOptional.isPresent()){
                    Token loginUserToken = tokenOptional.get();
                    String newAccessToken = jwtTokenProvider.validateRefreshToken(loginUserToken.getRefreshToken());
                    if(newAccessToken != null){
                        tokenRepository.updateAccessTokenById(loginUserToken.getId(), newAccessToken);
                        session.setAttribute("accessToken", newAccessToken);
                        log.info("Access token UPDATE USER_ID: {}, new AccessToken: {}", loginUserToken.getId(), newAccessToken);
                        return true;
                    }else{
                        tokenRepository.deleteById(userIdObj.toString());
                        session.removeAttribute("userId");
                        session.removeAttribute("userNickname");
                        session.removeAttribute("accessToken");

                        log.info("Refresh token expired, logout USER_ID: {}", loginUserToken.getId());
                        return false;
                    }
                }
            }else {
                log.info("Access token is not expired USER_ID: {}", userIdObj.toString());
                return true;
            }
        }

        return false;
    }
}
