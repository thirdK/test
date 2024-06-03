package com.example.finalapp.api;

import com.example.finalapp.dto.page.Criteria;
import com.example.finalapp.dto.page.Slice;
import com.example.finalapp.dto.reply.ReplyListDto;
import com.example.finalapp.dto.reply.ReplyUpdateDto;
import com.example.finalapp.dto.reply.ReplyWriteDto;
import com.example.finalapp.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyApi {
    private final ReplyService replyService;

    @PostMapping("/v1/boards/{boardId}/reply")
    public void replyWrite(@RequestBody ReplyWriteDto replyWriteDto,
                           @PathVariable("boardId") Long boardId,
                           @SessionAttribute("userId") Long userId){
        replyWriteDto.setBoardId(boardId);
        replyWriteDto.setUserId(userId);
        log.info("replyWriteDto = " + replyWriteDto + ", boardId = " + boardId);

        replyService.registerReply(replyWriteDto);
    }

    @GetMapping("/v1/boards/{boardId}/replies")
    public List<ReplyListDto> replyList(@PathVariable("boardId") Long boardId){
        return replyService.findList(boardId);
    }

    @GetMapping("/v2/boards/{boardId}/replies")
    public Slice<ReplyListDto> replySlice(@PathVariable("boardId") Long boardId,
                                         int page){
        Slice<ReplyListDto> slice = replyService.findSlice(new Criteria(page, 5), boardId);
        return slice;
    }

    @PatchMapping("/v1/replies/{replyId}")
    public void modifyReply(@RequestBody ReplyUpdateDto replyUpdateDto,
                            @PathVariable("replyId") Long replyId){

        replyUpdateDto.setReplyId(replyId);
        replyService.modifyReply(replyUpdateDto);
    }

    @DeleteMapping("/v1/replies/{replyId}")
    public void removeReply(@PathVariable("replyId") Long replyId){
        replyService.removeReply(replyId);
    }
}












