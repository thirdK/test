package com.example.finalapp.service.reply;

import com.example.finalapp.dto.page.Criteria;
import com.example.finalapp.dto.page.Slice;
import com.example.finalapp.dto.reply.ReplyListDto;
import com.example.finalapp.dto.reply.ReplyUpdateDto;
import com.example.finalapp.dto.reply.ReplyWriteDto;
import com.example.finalapp.mapper.reply.ReplyMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {
    @Mock
    ReplyMapper replyMapper;
    @InjectMocks
    ReplyService replyService;

    @Test
    void registerReply() {
        // given
        doNothing().when(replyMapper).insertReply(any());
        // when
        replyService.registerReply(new ReplyWriteDto());
        // then
        verify(replyMapper, times(1)).insertReply(any());
    }

    @Test
    void findList() {
        doReturn(List.of(new ReplyListDto())).when(replyMapper).selectList(any());

        List<ReplyListDto> list = replyService.findList(1L);

        assertThat(list).hasSize(1);
    }

    @Test
    void modifyReply() {
        doNothing().when(replyMapper).updateReply(any());

        replyService.modifyReply(new ReplyUpdateDto());

        verify(replyMapper, times(1)).updateReply(any());
    }

    @Test
    void removeReply() {
        doNothing().when(replyMapper).deleteReply(any());

        replyService.removeReply(1L);

        verify(replyMapper, times(1)).deleteReply(any());
    }

    @Test
    void findSlice(){
        // given
        List<ReplyListDto> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new ReplyListDto());
        }
        doReturn(list).when(replyMapper).selectSlice(any(), any());

        // when
        Slice<ReplyListDto> slice = replyService.findSlice(new Criteria(1, 3), 1L);
        // then
        assertThat(slice)
                .extracting("hasNext")
                .isEqualTo(true);

        assertThat(slice.getContentList())
                .hasSize(3);
    }
}












