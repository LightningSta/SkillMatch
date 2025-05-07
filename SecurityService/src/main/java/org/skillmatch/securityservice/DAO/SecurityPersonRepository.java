package org.skillmatch.securityservice.DAO;

import org.skillmatch.securityservice.DAO.Entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findByUsername(String username);
}
