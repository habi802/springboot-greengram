package kr.co.greengram.application.user;

import kr.co.greengram.application.user.model.UserProfileGetDto;
import kr.co.greengram.application.user.model.UserProfileGetRes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserProfileGetRes findProfileByUserId(UserProfileGetDto dto);
}
