package io.basic.security.security.provider;

import io.basic.security.security.common.FormWebAuthenticationDetails;
import io.basic.security.security.service.AccountContext;
import io.basic.security.security.token.AjaxAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //인증 검증
    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials(); //사용자가 입력한 password

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username); //저장된 password

        if (passwordEncoder.matches(password, accountContext.getAccount().getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

//        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
//        String secretKey = formWebAuthenticationDetails.getSecretKey();
//

        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
        System.out.println("ajaxAuthenticationToken = " + ajaxAuthenticationToken);
        return ajaxAuthenticationToken;

    }

    //assignable[əsáinəbl] 할당할 수 있는, 지정[지시]할 수 있는
    //파라미터로 전달되는 class의 타입과 provider의 token의 차이가 일치할때 true (인증을 처리하도록)
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
