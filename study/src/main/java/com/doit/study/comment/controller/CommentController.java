package com.doit.study.comment.controller;

import com.doit.study.comment.dto.CommentDto;
import com.doit.study.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 저장
     * @param writer_id
     * @param commentDto
     * @return ResponseEntity<Map<String, Integer>>
     */
    @PostMapping("/comments/save/{writer_id}")
    public ResponseEntity<Map<String, Integer>> write(
                        @PathVariable Integer writer_id,
                        @RequestBody CommentDto commentDto) throws Exception {
        Map<String, Integer> result = new HashMap<>();

        if (commentDto.getComment().length() > 500) { //댓글의 길이가 500자 이상인 경우
            result.put("result", 2);
            return ResponseEntity.ok().body(result);
        } else {
            commentService.insertComment(commentDto);
            int study_id = commentDto.getStudy_id();
            result.put("result", 1);
            return ResponseEntity.ok().body(result);
        }
    }

    /**
     * 답장 저장하기
     * @param writer_id
     * @param commentDto
     * @return ResponseEntity<Map<String, Integer>>
     * @throws Exception
     */
    @PostMapping("/comments/save/reply/{writer_id}")
    public ResponseEntity<Map<String, Integer>> writeReply(
                        @PathVariable Integer writer_id,
                        @RequestBody CommentDto commentDto) throws Exception {
        Map<String, Integer> result = new HashMap<>();

        if (commentDto.getComment().length() > 500) {
            log.info("길이는 ="+commentDto.getComment().length());
            result.put("result", 2);
            return ResponseEntity.ok().body(result);
        } else {
            commentService.insertComment(commentDto);
            int study_id = commentDto.getStudy_id();
            result.put("result", 1);
            return ResponseEntity.ok().body(result);
        }
    }

    /**
     * 댓글 수정
     * @param writer_id
     * @param commentDto
     * @return ResponseEntity
     * @throws Exception
     */
    @PutMapping("/comments/modify/reply/{writer_id}")
    public ResponseEntity modifyReply(
            @PathVariable Integer writer_id,
            @RequestBody CommentDto commentDto) throws Exception {

        commentService.updateComment(commentDto);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param comment_id
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/comments/delete/reply/{comment_id}")
    public ResponseEntity remove(@PathVariable Integer comment_id) throws Exception {

        commentService.deleteComment(comment_id);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
