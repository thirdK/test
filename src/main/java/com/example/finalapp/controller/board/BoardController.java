package com.example.finalapp.controller.board;

import com.example.finalapp.dto.board.BoardListDto;
import com.example.finalapp.dto.board.BoardUpdateDto;
import com.example.finalapp.dto.board.BoardViewDto;
import com.example.finalapp.dto.board.BoardWriteDto;
import com.example.finalapp.dto.page.Criteria;
import com.example.finalapp.dto.page.Page;
import com.example.finalapp.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String boardList(Criteria criteria, Model model){
        List<BoardListDto> boardList = boardService.findAllPage(criteria);
        int total = boardService.findTotal();
        Page page = new Page(criteria, total);

        model.addAttribute("boardList", boardList);
        model.addAttribute("page", page);

        return "board/list";
    }

    @GetMapping("/write")
    public String boardWrite(@SessionAttribute(value = "userId", required = false) Long userId){
        return userId == null ? "redirect:/user/login" :"board/write";
    }

    @PostMapping("/write")
    public String boardWrite(BoardWriteDto boardWriteDto,
                             @SessionAttribute("userId") Long userId,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("boardFile")List<MultipartFile> files){
//        Long userId = 1L;
        boardWriteDto.setUserId(userId);
        log.info("boardWriteDto = " + boardWriteDto);


//        files.forEach(file -> System.out.println(file.getOriginalFilename()));

//        boardService.registerBoard(boardWriteDto);
        try {
            boardService.registerBoardWithFiles(boardWriteDto, files);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        addFlashAttribute(키, 값) 은 리다이렉트 되는 url과 매핑된 컨트롤러 메소드의 model객체에
//        데이터를 저장시킨다.
//        /board/list와 매핑된 메소드의 model객체에 데이터를 저장한다.

//        redirectAttributes 객체로 데이터를 저장하는 방식
//        1. 쿼리스트링
//        컨트롤러 메서드의 매개변수로 데이터를 전달할 때 사용
//        컨트롤러에서 직접적으로 사용되는 데이터를 전달할 때 사용한다.

//        2. 플래시 영역
//        컨트롤러 메서드의 model객체에 데이터를 저장할 때 사용
//        최종적으로 띄워지는 화면에서 데이터를 사용할 목적으로 플래시에 저장한다.

        redirectAttributes.addFlashAttribute("boardId", boardWriteDto.getBoardId());

        return "redirect:/board/list";
    }

    @GetMapping("/view")
    public String boardView(Long boardId, Model model){
        BoardViewDto board = boardService.findById(boardId);
        model.addAttribute("board", board);

        return "board/view";
    }

    @GetMapping("/modify")
    public String boardModify(Long boardId, Model model){
        BoardViewDto board = boardService.findById(boardId);
        model.addAttribute("board", board);

        return "board/modify";
    }

    @PostMapping("/modify")
    public String boardModify(BoardUpdateDto boardUpdateDto,
                              @RequestParam("boardFile") List<MultipartFile> files,
                              RedirectAttributes redirectAttributes){

        try {
            boardService.modifyBoard(boardUpdateDto, files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        RedirectAttributes 객체는 스프링에서 지원하는 객체이다.
//        리다이렉트를 할 때 데이터를 들고 가는것은 일반적으로 불가능하다.
//        그나마 고려할 수 있는 방법은 쿼리스트링을 사용하는 것이다.
//        (브라우저가 리디렉션을 받으면 해당 url로 get요청을 하기 때문)
//        그래서 반환 문자열에 쿼리스트링을 직접 달아도 되지만, 스프링에서는 권장하지 않는다.
//        스프링에서는 RedirectAttributes 객체에 addAttribute(키, 값) 을 이용하면
//        자동으로 쿼리스트링을 만들어준다.
        redirectAttributes.addAttribute("boardId", boardUpdateDto.getBoardId());
        return "redirect:/board/view";
    }

    @GetMapping("/remove")
    public RedirectView removeBoard(Long boardId){
        boardService.removeBoard(boardId);
        return new RedirectView("/board/list");
    }


}













