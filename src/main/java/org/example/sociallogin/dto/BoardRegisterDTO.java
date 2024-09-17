package org.example.sociallogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRegisterDTO {

    private Long bno;
    private String title;
    private String content;
    private String writer;

    private MultipartFile[] images;

    private java.util.List<String> fileNames;

    private String tag;
}
