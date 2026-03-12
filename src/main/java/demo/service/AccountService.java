package demo.service;

import demo.entity.DTO.request.LoginRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    void saveAccount(LoginRequest loginRequest);
    void deleteAccount();
    void updateAccount();
}
