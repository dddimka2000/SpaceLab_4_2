package org.example.entity;

import org.springframework.security.core.GrantedAuthority;

/*

fixme

roles and authorities are different things
role = ROLE_DIRECTOR
authority/granted authority = DIRECTOR

why mix them up

 */

public enum UserRole implements GrantedAuthority {
    DIRECTOR,
    BRANCH_ADMINISTRATOR,
    BUILDER_ADMINISTRATOR;
    @Override
    public String getAuthority() {
        return name();
    }
}
