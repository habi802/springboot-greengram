package kr.co.greengram.application.follow;

import kr.co.greengram.entity.User;
import kr.co.greengram.entity.UserFollow;
import kr.co.greengram.entity.UserFollowIds;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public void postUserFollow(Long fromUserId, Long toUserId) {
        UserFollowIds userFollowIds = new UserFollowIds(fromUserId, toUserId);

        User fromUser = new User();
        fromUser.setUserId(fromUserId);

        User toUser = new User();
        toUser.setUserId(toUserId);

        UserFollow userFollow = UserFollow.builder()
                .userFollowIds(userFollowIds)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();

        followRepository.save(userFollow);
    }

    public void deleteUserFollow(Long fromUserId, Long toUserId) {
        UserFollowIds userFollowIds = new UserFollowIds(fromUserId, toUserId);
        followRepository.deleteById(userFollowIds);
    }
}
