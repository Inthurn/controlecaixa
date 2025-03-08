package br.com.inthurn.backend.transport;

import br.com.inthurn.backend.enums.RoleName;

public record CreateUserDTO(String email,
                            String password,
                            RoleName role) {
}
