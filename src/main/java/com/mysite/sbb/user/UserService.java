package com.mysite.sbb.user;

import com.mysite.sbb.question.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

   /* public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        //비밀번호 암호화 객체
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        this.userRepo.save(user);
        return user;
    }*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SiteUser createUser(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        //패스워드는 중요한 정보이기 때문에 바로 DB에 저장하지 않고 암호화 -> 암호화 객체 필요
        siteUser.setPassword(passwordEncoder.encode(password));

        userRepo.save(siteUser);
        return siteUser;
    }

    //사용자명으로 유저개체를 리턴
    public SiteUser getUser(String username) {
        Optional<SiteUser> _siteUser = userRepo.findByUsername(username);
        if (_siteUser.isPresent()) {
            return _siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
