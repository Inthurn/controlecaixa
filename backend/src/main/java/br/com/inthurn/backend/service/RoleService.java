package br.com.inthurn.backend.service;

import br.com.inthurn.backend.enums.RoleName;
import br.com.inthurn.backend.model.entity.Role;
import br.com.inthurn.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository ROLE_REPOSITORY;

    public RoleService(RoleRepository ROLE_REPOSITORY) {
        this.ROLE_REPOSITORY = ROLE_REPOSITORY;
    }

    public Optional<Role> getRoleByName(RoleName roleName) throws Exception {
        try {
            return ROLE_REPOSITORY.findByName(roleName);
        }catch (Exception e){
            throw new Exception("Não foi possível acessar Roles", e.getCause());
        }
    }
}
