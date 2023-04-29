package edu.tcu.cs.superfrogscheduler.user.security;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectAlreadyExistedException;
import edu.tcu.cs.superfrogscheduler.user.entity.SuperFrogUser;
import jakarta.transaction.Transactional;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    private final UserSecurityRepository userSecurityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserSecurityService(UserSecurityRepository userSecurityRepository, PasswordEncoder passwordEncoder) {
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public UserSecurity createUserSecurity(SuperFrogUser superFrogUser) {
//        UserSecurity userSecurity = UserSecurity.createUserSecurity(superFrogUser);
//        createBasicCredentials(userSecurity);
//
//        return this.userSecurityRepository.save(userSecurity);
//    }

    public Pair<UserSecurity, String> createUserSecurity(SuperFrogUser superFrogUser) {

        if (this.userSecurityRepository.findByEmail(superFrogUser.getEmail()).isPresent()) {
            throw new ObjectAlreadyExistedException("user", superFrogUser.getEmail());
        }

        UserSecurity userSecurity = UserSecurity.createUserSecurity(superFrogUser);
//        will add this when have notification system
//        second hashed is performed by createBasicCredentials() to store in db for security
        String defaultPassword = generateRandomPassword();
        userSecurity.setPassword(defaultPassword);
        createBasicCredentials(userSecurity);

        System.out.println(defaultPassword);

        return Pair.of(userSecurity, defaultPassword);
    }

    private String generateRandomPassword() {
        // generate algo:
        // 1. salt and hash -> unique password from randomString
        // 2. shuffle the hashedPassword
        // 3. random password is generated
        String random = this.passwordEncoder.encode("randomString");
        List<Character> characters = new ArrayList<>();

        for(int i = 0; i < random.length(); i++) {
            characters.add(random.charAt(i));
        }

        Collections.shuffle(characters);

        StringBuilder sb = new StringBuilder();
        for (char c: characters) {
            sb.append(c);
        }

        return sb.toString();

    }

    public void createBasicCredentials(UserSecurity userSecurity) {
        // salt and hashed default text password before saving
        String encodedPassword = this.passwordEncoder.encode(userSecurity.getPassword());

        userSecurity.setPassword(encodedPassword);
        userSecurity.setRoles("user");
        userSecurity.setEnabled(true);
    }

    public void deactivateUser(UserSecurity userSecurity) {
        userSecurity.setEnabled(false);
        this.userSecurityRepository.save(userSecurity);
    }

    // User will have email as username
    // loadUserbyUsername will be called by Spring authentication provider
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First, we need to find this user from database.
        // If found, wrap the returned user instance in a UserPrincipal instance (to satisfy contract with spring security)
        // Otherwise, throw an exception.
        return this.userSecurityRepository.findByEmail(email)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("user " + email + " is not found"));

    }
}
