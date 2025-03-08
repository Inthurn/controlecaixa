package br.com.inthurn.backend.transport;

import br.com.inthurn.backend.model.Role;

import java.util.List;

public record RecoveryUserDTO(Long id, String email, List<Role> roles) {
}
