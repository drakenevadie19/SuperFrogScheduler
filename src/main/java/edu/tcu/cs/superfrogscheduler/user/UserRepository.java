package edu.tcu.cs.superfrogscheduler.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SuperFrogUser, String> {
    Optional<SuperFrogUser> findByEmail(String email);

}
