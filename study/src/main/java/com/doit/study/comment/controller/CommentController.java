package com.doit.study.comment.controller;

import com.doit.study.comment.dto.CommentDto;
import com.doit.study.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/save/{writer_id}")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> write(
                        @PathVariable Integer writer_id,
                        @RequestBody CommentDto commentDto) {
        log.info("writer_id={}", writer_id);
        log.info("commentDto={}", commentDto);

        Map<String, Integer> result = new HashMap<>();

        if (commentDto.getComment().length() > 500) {
            log.info("길이는 ="+commentDto.getComment().length());
            result.put("result", 2);
            return ResponseEntity.ok().body(result);
        } else {
            try {
                commentService.insertComment(commentDto);
                int study_id = commentDto.getStudy_id();
                result.put("result", 1);
                return ResponseEntity.ok().body(result);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("result", 3);
                return ResponseEntity.ok().body(result);
            }
        }
    }

    @PostMapping("/comments/save/reply/{writer_id}")
    public ResponseEntity<Map<String, Integer>> writeReply(
                        @PathVariable Integer writer_id,
                        @RequestBody CommentDto commentDto) {
        log.info("writer_id={}", writer_id);
        log.info("commentDto={}", commentDto);

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

    @PutMapping("/comments/modify/reply/{writer_id}")
    public ResponseEntity modifyReply(
            @PathVariable Integer writer_id,
            @RequestBody CommentDto commentDto) {

        log.info("수정");
        log.info("writer_id={}", writer_id);
        log.info("commentDto={}", commentDto);

        commentService.updateComment(commentDto);

        return ResponseEntity.ok(writer_id);
    }

    @DeleteMapping("/comments/delete/reply/{comment_id}")
    public ResponseEntity remove(
            @PathVariable Integer comment_id){

        commentService.deleteComment(comment_id);

        return ResponseEntity.ok(comment_id);
    }
}
