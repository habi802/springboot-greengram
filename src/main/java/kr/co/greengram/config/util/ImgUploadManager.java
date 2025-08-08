package kr.co.greengram.config.util;

import kr.co.greengram.config.constants.ConstFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImgUploadManager {
    private final ConstFile constFile;
    private final MyFileUtils myFileUtils;

    public List<String> saveFeedPics(long feedId, List<MultipartFile> pics) {
        String directory = String.format("%s/%s/%d", constFile.getUploadDirectory(), constFile.getFeedPic(), feedId);
        myFileUtils.makeFolders(directory);

        List<String> randomFileNames = new ArrayList<>(pics.size());
        for (MultipartFile pic : pics) {
            String randomFileName = myFileUtils.makeRandomFileName(pic);
            randomFileNames.add(randomFileName);

            String savePath = directory + "/" + randomFileName;

            try {
                myFileUtils.transferTo(pic, savePath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "피드 이미지 저장에 실패하였습니다.");
            }
        }

        return randomFileNames;
    }

    // 저장할 파일명 리턴
    public String saveProfilePic(long id, MultipartFile profilePicFile) {
        // 폴더 생성
        String directory = String.format("%s/%s/%d", constFile.getUploadDirectory(), constFile.getProfilePic(), id);
        myFileUtils.makeFolders(directory);

        String randomFileName = myFileUtils.makeRandomFileName(profilePicFile);
        String savePath = directory + "/" + randomFileName;

        try {
            myFileUtils.transferTo(profilePicFile, savePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 이미지 저장에 실패하였습니다.");
        }

        return randomFileName;
    }
}
