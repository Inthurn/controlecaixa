package br.com.inthurn.backend.controller;

import br.com.inthurn.backend.service.UserService;
import br.com.inthurn.backend.model.transport.CreateUserDTO;
import br.com.inthurn.backend.model.transport.LoginUserDTO;
import br.com.inthurn.backend.model.transport.RecoveryJWTTokenDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService USER_SERVICE;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJWTTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDTO) {
        RecoveryJWTTokenDTO token = USER_SERVICE.authenticatUser(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        USER_SERVICE.createUser(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
