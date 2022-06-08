package com.doit.study.note.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.mapper.NoteMapper;
import com.doit.study.mapper.ProfileMapper;
import com.doit.study.note.domain.Note;
import com.doit.study.note.dto.NoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteMapper noteMapper;
    private final ProfileMapper profileMapper;
    private final MemberMapper memberMapper;

    @Override
    public void saveNote(NoteDto noteDto) {
        log.info("noteDto={}", noteDto);

        //보내는 사람 노트 DB 저장
        Note note = noteDto.toEntity(noteDto);
        noteMapper.saveSenderNote(note);

        //받는 사람 노트 DB 저장
        noteMapper.saveReceiverNote(note);
    }

    @Override
    public List<NoteDto> getAlarmMessage(Integer id) {
        log.info("id={}", id);

        List<Note> noteList = noteMapper.getAlarm(id);

        List<NoteDto> noteDtos = new ArrayList<>();

        if(!noteList.isEmpty()) {
            for (Note note : noteList) {
                NoteDto noteDto = new NoteDto();

                //알림 내용 설정
                String content = noteDto.makeMsg(note.getGubun(), note.getTitle());
                noteDto.setContent(content);

                //경로 설정
                if (note.getGubun() == 0) {
                    noteDto.setPath("/note/" + note.getReceiver_id());
                } else {

                }
                log.info("noteDto = {}", noteDto);

                noteDtos.add(noteDto);
            }
        }

        return noteDtos;
    }

    @Override
    public List<NoteDto> getNote(Integer id) {
        log.info("id={}", id);

        List<Note> noteList = noteMapper.getNote(id);

        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : noteList) {
            NoteDto noteDto = new NoteDto().toDto(note);

            //sender 프로필 사진 가져오기
            String senderPath = profileMapper.getImagePath(note.getSender_id());
            if(senderPath != null) {
                noteDto.setSender_img_path(senderPath);
            }

            //receiver 프로필 사진 가져오기
            String receiverPath = profileMapper.getImagePath(note.getReceiver_id());
            if(receiverPath != null) {
                noteDto.setReceiver_img_path(receiverPath);
            }

            //sender 닉네임 가져오기
            String senderNickName = memberMapper.findNickname(note.getSender_id());
            noteDto.setSender_nickname(senderNickName);

            //receiver 닉네임 가져오기
            String receiverNickname = memberMapper.findNickname(note.getReceiver_id());
            noteDto.setReceiver_nickname(receiverNickname);

            noteDtos.add(noteDto);
        }

        return noteDtos;
    }

    @Override
    public Integer updateReadYN(Integer id) {
        return noteMapper.updateReadYN(id);
    }

    @Override
    public void deleteNote(Integer note_id) {
        noteMapper.deleteNote(note_id);
    }

}
