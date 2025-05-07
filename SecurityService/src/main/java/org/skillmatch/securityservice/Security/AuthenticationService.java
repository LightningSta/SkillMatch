package org.skillmatch.securityservice.Security;

import lombok.RequiredArgsConstructor;
import org.skillmatch.securityservice.DAO.Entity.PersonEntity;
import org.skillmatch.securityservice.Services.SecurityPersonService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final SecurityPersonService securityPersonService;
    private final PasswordEncoder passwordEncoder;

    public PersonAuthentication authenticate(final String username, final String password) {
        final PersonEntity person = securityPersonService.findPersonByUsername(username);
        if (Objects.isNull(person) || !passwordEncoder.matches(password, person.getPassword())) {
            return null;
        }else{
            return PersonAuthentication.builder()
                    .role(person.getRole())
                    .userName(person.getUsername())
                    .isActive(true)
                    .build();
        }
    }


}
