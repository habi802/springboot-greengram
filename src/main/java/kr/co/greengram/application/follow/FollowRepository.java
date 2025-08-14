package kr.co.greengram.application.follow;

import kr.co.greengram.entity.UserFollow;
import kr.co.greengram.entity.UserFollowIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<UserFollow, UserFollowIds> {

}
