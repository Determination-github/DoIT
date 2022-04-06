package com.doit.study.comment.controller;

import com.doit.study.comment.dto.CommentDto;
import com.doit.study.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PatchMapping("/comments/{comment_Id}")
    public ResponseEntity<String> modify(@PathVariable Integer comment_Id, @RequestBody CommentDto commentDto, HttpSession session) {
        String comment_Writer = (String)session.getAttribute("nickName");
        commentDto.setComment_Writer(comment_Writer);
        commentDto.setComment_Id(comment_Id);
       log.info("dto = " + commentDto);

        try {
            if(commentService.modify(commentDto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("댓글이 수정되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("댓글 수정이 실패되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto commentDto, Integer board_Id, HttpSession session
                                        ) {

        if(commentDto.getParentComment_Id()==null)
            commentDto.setParentComment_Id(0);

        String comment_Writer = (String)session.getAttribute("nickName");
        commentDto.setComment_Writer(comment_Writer);
        commentDto.setBoard_Id(board_Id);
        log.info("commentDto = " + commentDto);

        try {
            if(commentService.write(commentDto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("댓글이 작성되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("댓글 작성이 실패되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comments/{comment_Id}")
    public ResponseEntity<String> remove(@PathVariable Integer comment_Id, Integer board_Id, HttpSession session, CommentDto commentDto){
        String comment_Writer = (String)session.getAttribute("nickName");
        try {
            int rowCnt = commentService.remove(comment_Id, board_Id, comment_Writer);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("댓글 삭제가 실패되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> list(Integer board_Id){
        List<CommentDto> list = null;
        try {
            list = commentService.getList(board_Id);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.BAD_REQUEST);
        }
    }
}
