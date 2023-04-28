package edu.tcu.cs.superfrogscheduler.security.auth;

import edu.tcu.cs.superfrogscheduler.user.converter.Converter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.user.security.UserPrincipal;
import edu.tcu.cs.superfrogscheduler.user.security.UserSecurity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final Converter converter;

    public AuthService(JwtProvider jwtProvider, Converter converter) {
        this.jwtProvider = jwtProvider;
        this.converter = converter;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        // Create user info
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserSecurity userSecurity = principal.getUserSecurity();

        UserDto userDto = this.converter.toUserDto(userSecurity.getUser());

        // create JWT
        String token = this.jwtProvider.createToken(authentication);
        Map<String, Object> loginResultMap = new HashMap<>();

        // create map
        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);

        return loginResultMap;
    }

    public void validateAdmin(Authentication authentication) {
        if (!isAdmin(authentication)) {
            throw new AccessDeniedException("Unauthorized");
        }
    }

    // to validate user whose authenticated id does match userId want to perform any action
    // admin is allowed
    public void validateUserOwner(Authentication authentication, String userId) {
        System.out.println(isAdmin(authentication));

        if (!isAdmin(authentication) && !Objects.equals(authentication.getName(), userId)) {
            throw new AccessDeniedException("Unauthorized");
        }
    }

    private boolean isAdmin(Authentication authentication) {

        return authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_admin"));
    }

}
