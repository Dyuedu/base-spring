package demo.service.Impl;

import demo.entity.Account;
import demo.entity.DTO.request.LoginRequest;
import demo.repository.AccountRepository;
import demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveAccount(LoginRequest loginRequest) {
            Account account = new Account();
            account.setUsername(loginRequest.getUsername());
            account.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
            accountRepository.save(account);
    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public void updateAccount() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
