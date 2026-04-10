package demo.configuration;

import demo.entity.Account;
import demo.service.AccountService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationPvd implements Converter<Jwt, AbstractAuthenticationToken> {

    private final AccountService accountService; // Inject repository của bạn

    public AuthenticationPvd(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();

        Account account = (Account) accountService.loadUserByUsername(username);

        return new JwtAuthenticationToken(jwt, account.getAuthorities());
    }
}
