package com.example.finalapp.mapper.board;

import com.example.finalapp.dto.board.BoardListDto;
import com.example.finalapp.dto.board.BoardUpdateDto;
import com.example.finalapp.dto.board.BoardViewDto;
import com.example.finalapp.dto.board.BoardWriteDto;
import com.example.finalapp.dto.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    void insertBoard(BoardWriteDto boardWriteDto);

    void deleteBoard(Long boardId);

    void updateBoard(BoardUpdateDto boardUpdateDto);

    Optional<BoardViewDto> selectById(Long boardId);

    List<BoardListDto> selectAll();

    List<BoardListDto> selectAllPage(Criteria criteria);

    int selectTotal();
}

















