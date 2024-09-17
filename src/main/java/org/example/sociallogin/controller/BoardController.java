package org.example.sociallogin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.sociallogin.dto.BoardReadDTO;
import org.example.sociallogin.dto.BoardRegisterDTO;
import org.example.sociallogin.dto.PageRequest;
import org.example.sociallogin.service.BoardService;
import org.example.sociallogin.util.UploadUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final UploadUtil uploadUtil;

    private final BoardService boardService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/register")
    public String register(
            BoardRegisterDTO boardRegisterDTO,
            RedirectAttributes rttr) {

        log.info("Registering board: " + boardRegisterDTO);

        List<String> uploadedFileNames = uploadUtil.upload(boardRegisterDTO.getImages());

        boardRegisterDTO.setFileNames(uploadedFileNames);

        //서비스 등록
        boardService.register(boardRegisterDTO);

        rttr.addFlashAttribute("bno", boardRegisterDTO.getBno());

        return "redirect:/board/list";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/register")
    public void register(){

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void list(PageRequest pageRequest, Model model) {

        model.addAttribute("result", boardService.list(pageRequest));

    }

    @PreAuthorize("isAuthenticated()") // 로그인된 사용자만 호출할 수 있도록 제한
    @GetMapping("/read/{bno}")
    public String read (@CookieValue(required = false, value = "view", defaultValue = "") String viewValue, @PathVariable("bno") Long bno, Model model) {

        log.info("Reading board: " + bno);
        log.info("viewValue: " + viewValue);

        boolean existed = false;

        if(viewValue != null) {
            existed = Arrays.stream(viewValue.split("%")).anyMatch(str -> str.equals(bno+""));
        }

        if(!existed) {

            log.info("View Count... update........................");

        }

        Optional<BoardReadDTO> result = boardService.get(bno);

        BoardReadDTO boardReadDTO = result.orElseThrow();

        model.addAttribute("board", boardReadDTO);

        return "/board/read";
    }

    @GetMapping("/ex2")
    public void ex2(Model model) {
        log.info("ex2");
    }
}
