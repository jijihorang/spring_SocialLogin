package org.example.sociallogin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.sociallogin.dto.*;
import org.example.sociallogin.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public void register(BoardRegisterDTO boardRegisterDTO) {

        boardMapper.insert(boardRegisterDTO);

        List<String> fileNames = boardRegisterDTO.getFileNames();

        for (int i = 0; i < fileNames.size() ; i++) {
            boardMapper.insertAttach(boardRegisterDTO.getBno(),
                    fileNames.get(i),
                    i);
        }//end for
    }

    @Transactional(readOnly = true)
    public PageResponse<BoardListDTO> list(PageRequest pageRequest) {

        List<BoardListDTO> dtoList = boardMapper.listImage(pageRequest);

        int total = boardMapper.count(pageRequest);

        return PageResponse.<BoardListDTO>with()
                .list(dtoList)
                .total(total)
                .pageRequest(pageRequest)
                .build();
    }

    @Transactional(readOnly = true)
    public Optional<BoardReadDTO> get(Long bno) {
        return boardMapper.select(bno);
    }
}
