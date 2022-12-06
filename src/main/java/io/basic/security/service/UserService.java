package io.basic.security.service;

import io.basic.security.domain.Account;
import org.springframework.stereotype.Service;

public interface UserService {

    void createUser(Account account);

}
