package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    //이넘으로 관리자, 유저
    ADMIN ("ROLE_ADMIN"),
    USER ("ROLE_USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
