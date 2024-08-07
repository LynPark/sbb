package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<SiteUser, Long> {
    //기본 id로 찾는 메서드는 있지만 유저네임으로 찾는 메서드는 이름을 만들어줌
    Optional<SiteUser> findByUsername(String username);
}
