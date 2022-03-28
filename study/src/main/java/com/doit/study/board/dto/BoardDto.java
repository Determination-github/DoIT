package com.doit.study.board.dto;

//import com.doit.study.Board.domain.Board;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int     board_Id;
    private String  board_Title;
    private String  board_Content;
    private int     board_Count;
    private int     board_Comment;
    private Date    board_Date;
    private Boolean board_Notify;
    private String  board_Writer;

}
