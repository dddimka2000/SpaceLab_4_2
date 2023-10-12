package org.example.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    DIRECTOR,
    BRANCH_ADMINISTRATOR,
    BUILDER_ADMINISTRATOR;
    @Override
    public String getAuthority() {
        return name();
    }
}
