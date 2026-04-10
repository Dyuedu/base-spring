package demo.service.Impl;

import demo.entity.Account;
import demo.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        // Init data
        account = new Account();
        account.setUsername("username");
        account.setPassword("password");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveAccount() {
    }

    @Test
    void deleteAccount() {
        account.setUsername("b");
        accountService.deleteAccount();

        System.out.println("delete test case: " + account.toString());
    }

    @Test
    void updateAccount() {
    }

    @Test
    void loadUserByUsername_hasData_returnUser() {

        account.setUsername("username");
        System.out.println("select data test case: " + account.toString());

        // Mock data from DB response
        Mockito.when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));

        // Call to service get data
        Account user = accountService.loadUserByUsername("username");

        // Verify data
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());

        Mockito.verify(accountRepository, Mockito.times(3)).findByUsername("username");
    }

    @Test
    void loadUserByUsername_noData_throwException() {
        Mockito.when(accountRepository.findByUsername("username")).thenReturn(Optional.empty());


        try {
            accountService.loadUserByUsername("username");
        } catch (UsernameNotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }

        // investigate more
//        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername("username"));
    }
}