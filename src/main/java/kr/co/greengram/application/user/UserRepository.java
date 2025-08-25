package kr.co.greengram.application.user;

import kr.co.greengram.config.security.SignInProviderType;
import kr.co.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUid(String uid);
    User findByUidAndProviderType(String uid, SignInProviderType signInProviderType);
}
