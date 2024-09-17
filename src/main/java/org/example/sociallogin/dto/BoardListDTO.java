package org.example.sociallogin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDTO {

    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private int replyCnt;
    private String fileName;

    private int viewCnt;
}
