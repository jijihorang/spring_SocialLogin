package org.example.sociallogin.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.sociallogin.dto.BoardListDTO;
import org.example.sociallogin.dto.BoardReadDTO;
import org.example.sociallogin.dto.BoardRegisterDTO;
import org.example.sociallogin.dto.PageRequest;

import java.util.Optional;

public interface BoardMapper {

    int insert(BoardRegisterDTO registerDTO);

    int insertAttach(@Param("bno")Long bno,
                     @Param("fileName")String fileName,
                     @Param("ord")int ord);

    java.util.List<BoardListDTO> listImage(PageRequest pageRequest);

    int count(PageRequest pageRequest);

    Optional<BoardReadDTO> select(Long bno);

}
