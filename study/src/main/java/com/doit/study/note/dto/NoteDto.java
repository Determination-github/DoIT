package com.doit.study.note.dto;

import com.doit.study.note.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private Integer note_id;
    private Integer user_id;
    private Integer receiver_id;
    private Integer sender_id;
    private String title;
    private String content;
    private Date reg_date;

    private String study_id;

    //닉네임 설정
    private String sender_nickname;
    private String receiver_nickname;

    //알림 클릭시 이동을 위한 경로설정
    private String path;

    //프로필 이미지 경로
    private String sender_img_path;
    private String receiver_img_path;

    public Note toEntity(NoteDto noteDto) {
        return Note.builder()
                .user_id(user_id)
                .receiver_id(receiver_id)
                .content(content)
                .title(title)
                .build();
    }

    public NoteDto toDto(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setNote_id(note.getNote_id());
        noteDto.setUser_id(note.getUser_id());
        noteDto.setSender_id(note.getSender_id());
        noteDto.setReceiver_id(note.getReceiver_id());
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());
        noteDto.setReg_date(note.getReg_date());
        return noteDto;
    }

    public String makeMsg(int gubun, String title) {
        if(gubun == 0) {
            title = "[쪽지], " + HtmlUtils.htmlEscape(title);
        } else {
            title = "[댓글], " + HtmlUtils.htmlEscape(title);
        }

        return title;
    }

}


