package br.com.inthurn.backend.service;

import br.com.inthurn.backend.auth.JwtTokenService;
import br.com.inthurn.backend.auth.configuration.SecurityConfiguration;
import br.com.inthurn.backend.auth.implementations.UserDetailsImpl;
import br.com.inthurn.backend.enums.RoleName;
import br.com.inthurn.backend.model.Role;
import br.com.inthurn.backend.model.User;
import br.com.inthurn.backend.repository.UserRepository;
import br.com.inthurn.backend.transport.CreateUserDTO;
import br.com.inthurn.backend.transport.LoginUserDTO;
import br.com.inthurn.backend.transport.RecoveryJWTTokenDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final JwtTokenService JWT_TOKEN_SERVICE;
    private final RoleService ROLE_SERVICE;
    private final UserRepository USER_REPOSITORY;
    private final SecurityConfiguration SECURITY_CONFIGURATION;

    public UserService(AuthenticationManager AUTHENTICATION_MANAGER, JwtTokenService JWT_TOKEN_SERVICE,
                       RoleService ROLE_SERVICE, UserRepository USER_REPOSITORY, SecurityConfiguration SECURITY_CONFIGURATION) {
        this.AUTHENTICATION_MANAGER = AUTHENTICATION_MANAGER;
        this.JWT_TOKEN_SERVICE = JWT_TOKEN_SERVICE;
        this.ROLE_SERVICE = ROLE_SERVICE;
        this.USER_REPOSITORY = USER_REPOSITORY;
        this.SECURITY_CONFIGURATION = SECURITY_CONFIGURATION;
    }

    public RecoveryJWTTokenDTO authenticatUser(LoginUserDTO loginUserDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.password());

        Authentication authentication = AUTHENTICATION_MANAGER.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl usersDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJWTTokenDTO(JWT_TOKEN_SERVICE.generateToken(usersDetails));
    }

    public void createUser(CreateUserDTO createUserDTO) throws Exception {
        Optional<Role> defaultRole = ROLE_SERVICE.getRoleByName(RoleName.ROLE_USER);

        User user = User.builder()
                .email(createUserDTO.email())
                .password(SECURITY_CONFIGURATION.passwordEncoder().encode(createUserDTO.password()))
                .roles(defaultRole.isPresent() ? null : List.of(Role.builder().name(RoleName.ROLE_USER).build()))
                .build();

        USER_REPOSITORY.save(user);
    }
}
