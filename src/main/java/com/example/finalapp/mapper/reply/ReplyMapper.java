package com.example.finalapp.mapper.reply;

import com.example.finalapp.dto.page.Criteria;
import com.example.finalapp.dto.reply.ReplyListDto;
import com.example.finalapp.dto.reply.ReplyUpdateDto;
import com.example.finalapp.dto.reply.ReplyWriteDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    void insertReply(ReplyWriteDto replyWriteDto);

    List<ReplyListDto> selectList(Long boardId);

    void updateReply(ReplyUpdateDto replyUpdateDto);

    void deleteReply(Long replyId);

    List<ReplyListDto> selectSlice(@Param("criteria") Criteria criteria,
                                   @Param("boardId") Long boardId);
}
















