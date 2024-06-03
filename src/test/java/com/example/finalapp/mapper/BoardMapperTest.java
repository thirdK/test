package com.example.finalapp.mapper;

import com.example.finalapp.dto.board.BoardListDto;
import com.example.finalapp.dto.board.BoardUpdateDto;
import com.example.finalapp.dto.board.BoardViewDto;
import com.example.finalapp.dto.board.BoardWriteDto;
import com.example.finalapp.dto.page.Criteria;
import com.example.finalapp.dto.user.UserDto;
import com.example.finalapp.mapper.board.BoardMapper;
import com.example.finalapp.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
class BoardMapperTest {
    @Autowired
    BoardMapper boardMapper;
    @Autowired
    UserMapper userMapper;
    BoardWriteDto boardWriteDto;
    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setLoginId("test");
        userDto.setPassword("1234");
        userDto.setEmail("test@naver.com");
        userDto.setGender("M");
        userDto.setAddress("노원구");
        userDto.setAddressDetail("123호");
        userDto.setZipcode("12345");

        userMapper.insertUser(userDto);

        boardWriteDto = new BoardWriteDto();
        boardWriteDto.setTitle("test title");
        boardWriteDto.setContent("test content");
        boardWriteDto.setUserId(userDto.getUserId());

        boardMapper.insertBoard(boardWriteDto);
    }

    @Test
    void deleteBoard() {
        // given
        // when
        boardMapper.deleteBoard(boardWriteDto.getBoardId());
        BoardViewDto boardViewDto = boardMapper.selectById(boardWriteDto.getBoardId()).orElse(null);
        // then
        assertThat(boardViewDto).isNull();
    }

    @Test
    void deleteBoard2() {
        // given
        // when
        boardMapper.deleteBoard(boardWriteDto.getBoardId());

        // then
        boardMapper.selectById(boardWriteDto.getBoardId())
                .ifPresentOrElse((dto) -> fail("삭제 실패"), () -> {});
    }

    @Test
    void updateBoard() {
        // given
        BoardUpdateDto boardUpdateDto = new BoardUpdateDto();
        boardUpdateDto.setTitle("update title");
        boardUpdateDto.setContent("update content");
        boardUpdateDto.setBoardId(boardWriteDto.getBoardId());

        // when
        boardMapper.updateBoard(boardUpdateDto);
        BoardViewDto boardViewDto = boardMapper.selectById(boardUpdateDto.getBoardId()).orElse(null);

        // then
        assertThat(boardViewDto) // 검증 대상 설정
                .isNotNull() // null이 아닌지 검사
                .extracting("title")// 검증 대상의 특정 필드를 가져옴
                .isEqualTo(boardUpdateDto.getTitle()); // 수정dto에 들어있는 타이틀과 일치하는지 검사
    }

    @Test
    void selectAll() {
        // given
        // when
        List<BoardListDto> boardList = boardMapper.selectAll();
        // then
        assertThat(boardList)// 검증 대상 설정
                .isNotEmpty()// 비어있지 않은지 검사
                .extracting("title") // 리스트에 담긴 dto들의 title 필드만 가져오기
                .contains(boardWriteDto.getTitle()); // 여러 title 중에 특정 값이 포함되었는지 검사
    }

    @Test
    void selectAllPage(){
        // given
        Criteria criteria = new Criteria();
        criteria.setPage(1);
        criteria.setAmount(10);

        // when
        List<BoardListDto> list = boardMapper.selectAllPage(criteria);

        // then
        assertThat(list).hasSize(10);
    }
}
























