package kr.co.greengram.application.feed;

import kr.co.greengram.application.feed.model.FeedGetDto;
import kr.co.greengram.application.feed.model.FeedGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    List<FeedGetRes> findAllLimitedTo(FeedGetDto dto);
}
