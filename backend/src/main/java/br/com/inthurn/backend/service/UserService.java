package br.com.inthurn.backend.service;

import br.com.inthurn.backend.auth.JwtTokenService;
import br.com.inthurn.backend.auth.configuration.SecurityConfiguration;
import br.com.inthurn.backend.auth.implementations.UserDetailsImpl;
import br.com.inthurn.backend.enums.RoleName;
import br.com.inthurn.backend.model.entity.Role;
import br.com.inthurn.backend.model.entity.User;
import br.com.inthurn.backend.repository.UserRepository;
import br.com.inthurn.backend.model.transport.CreateUserDTO;
import br.com.inthurn.backend.model.transport.LoginUserDTO;
import br.com.inthurn.backend.model.transport.RecoveryJWTTokenDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final JwtTokenService JWT_TOKEN_SERVICE;
    private final RoleService ROLE_SERVICE;
    private final UserRepository USER_REPOSITORY;
    private final SecurityConfiguration SECURITY_CONFIGURATION;

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
