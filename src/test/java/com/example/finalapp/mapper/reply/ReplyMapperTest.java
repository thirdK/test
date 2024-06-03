package com.example.finalapp.mapper.reply;

import com.example.finalapp.dto.reply.ReplyListDto;
import com.example.finalapp.dto.reply.ReplyUpdateDto;
import com.example.finalapp.dto.reply.ReplyWriteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Transactional
class ReplyMapperTest {
    @Autowired
    ReplyMapper replyMapper;

    ReplyWriteDto replyWriteDto;

    @BeforeEach
    void setUp() {
        replyWriteDto = new ReplyWriteDto();
        replyWriteDto.setContent("test content");
        replyWriteDto.setBoardId(1L);
        replyWriteDto.setUserId(1L);
    }

    @Test
    void insertReply() {
        replyMapper.insertReply(replyWriteDto);

        List<ReplyListDto> list = replyMapper.selectList(1L);

        assertThat(list.get(0))
                .extracting("content")
                .isEqualTo(replyWriteDto.getContent());
    }

    @Test
    void selectList() {
    }

    @Test
    void updateReply() {
        replyMapper.insertReply(replyWriteDto);

        ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto();
        replyUpdateDto.setContent("update");
        replyUpdateDto.setReplyId(1L);


        replyMapper.updateReply(replyUpdateDto);

        List<ReplyListDto> list = replyMapper.selectList(1L);

        assertThat(list)
                .hasSize(1)
                .extracting("content")
                .contains("update");
    }

    @Test
    void deleteReply() {
        replyMapper.insertReply(replyWriteDto);

        replyMapper.deleteReply(1L);
        List<ReplyListDto> list = replyMapper.selectList(1L);

        assertThat(list).isEmpty();
    }
}