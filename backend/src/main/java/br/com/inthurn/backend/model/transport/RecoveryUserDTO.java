package br.com.inthurn.backend.model.transport;

import br.com.inthurn.backend.model.entity.Role;

import java.util.List;

public record RecoveryUserDTO(Long id, String email, List<Role> roles) {
}
