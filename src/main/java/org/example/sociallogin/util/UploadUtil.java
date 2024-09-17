package org.example.sociallogin.util;

import lombok.extern.log4j.Log4j2;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component // bean 등록
public class UploadUtil {

    @Value("${board.upload}")
    private String folder;

    public List<String> upload(MultipartFile[] files) throws RuntimeException {

        List<String> list = new ArrayList<String>();

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {

                log.info(file.getName());
                log.info(file.getContentType());
                log.info(file.getSize());
                log.info("------------------------------");

                String fileName = file.getOriginalFilename();

                String saveFileName = UUID.randomUUID().toString() + "_" + fileName;

                File copyFile = new File(folder, saveFileName);

                try {
                    FileCopyUtils.copy(file.getBytes(), copyFile);

                    //이미지 파일 이라면 썸네일 생성
                    if (file.getContentType().startsWith("image")) {

                        Thumbnails.of(copyFile)
                                .size(160, 160)
                                .toFile(new File(folder, "s_" + saveFileName));


                    }
                    list.add(saveFileName);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return list;
    }

}
