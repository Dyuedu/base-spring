package demo.filter;

import demo.configuration.JwtTokenProvider;
import demo.entity.Account;
import demo.service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(@Lazy AccountService accountService, JwtTokenProvider jwtTokenProvider,@Lazy AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rawToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(rawToken) || !rawToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = rawToken.substring(7);
        if(jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = accountService.loadUserByUsername(username);
            System.out.println("userDetails: " + userDetails.isAccountNonLocked());
            UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
//            UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request, response);
    }
}
