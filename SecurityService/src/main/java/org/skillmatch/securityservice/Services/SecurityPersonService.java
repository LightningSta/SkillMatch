package org.skillmatch.securityservice.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.securityservice.DAO.Entity.PersonEntity;
import org.skillmatch.securityservice.DAO.SecurityPersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityPersonService {

    private final SecurityPersonRepository securityPersonRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonEntity findPersonByUsername(String username) {
        return securityPersonRepository.findByUsername(username);
    }

    public boolean addPerson(PersonEntity person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return securityPersonRepository.save(person) != null;
    }
}
