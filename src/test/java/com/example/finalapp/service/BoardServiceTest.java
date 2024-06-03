package com.example.finalapp.service;

import com.example.finalapp.dto.board.BoardListDto;
import com.example.finalapp.dto.board.BoardViewDto;
import com.example.finalapp.dto.board.BoardWriteDto;
import com.example.finalapp.mapper.board.BoardMapper;
import com.example.finalapp.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    BoardMapper boardMapper;
    @InjectMocks
    BoardService boardService;

    @Test
    void registerBoard() {
        // given
        doNothing().when(boardMapper).insertBoard(any());
        // when
        boardService.registerBoard(new BoardWriteDto());
        // then
        verify(boardMapper, times(1)).insertBoard(any());
    }

    @Test
    void removeBoard() {
        // given
        doNothing().when(boardMapper).deleteBoard(any());
        // when
        boardService.removeBoard(1L);
        // then
        verify(boardMapper, times(1)).deleteBoard(any());
    }

    @Test
    void modifyBoard() {
//        // given
//        doNothing().when(boardMapper).updateBoard(any());
//        // when
//        boardService.modifyBoard(new BoardUpdateDto());
//        // then
//        verify(boardMapper, times(1)).updateBoard(any());
    }

    @Test
    void findById() {
        // given
        BoardViewDto testDto = new BoardViewDto();
//        testDto.setBoardId(1L);

        doReturn(Optional.of(testDto)).when(boardMapper).selectById(any());

        // when
        BoardViewDto foundBoard = boardService.findById(1L);

        // then
        assertThat(foundBoard)
                .isNotNull()
                .extracting("boardId")
                .isEqualTo(1L);
    }

    @Test
    void findByIdException(){
        // given
        doReturn(Optional.empty()).when(boardMapper).selectById(any());
        // when
        // then
        assertThatThrownBy(() -> boardService.findById(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("유효하지 않은");
    }

    @Test
    void classTest(){
        Class<BoardService> clazz = BoardService.class;
        Class<? extends BoardService> aClass = boardService.getClass();
        System.out.println("test = " + BoardService.class);

        System.out.println(clazz.getName());
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));
        Arrays.stream(clazz.getMethods()).map(ele -> ele.getName()).forEach(System.out::println);
    }

    @Test
    void findAll() {
        // given
        doReturn(List.of(new BoardListDto())).when(boardMapper).selectAll();
        // when
        List<BoardListDto> list = boardService.findAll();
        // then
        assertThat(list)
                .hasSize(1);
    }
}