package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SuperFrogUser, String>, JpaSpecificationExecutor<SuperFrogUser> {
    Optional<SuperFrogUser> findByEmail(String email);

    Optional<SuperFrogUser> findById(String id);
}
