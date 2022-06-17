package com.doit.study.note.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.mapper.NoteMapper;
import com.doit.study.mapper.ProfileMapper;
import com.doit.study.note.domain.Note;
import com.doit.study.note.dto.NoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoteServiceImpl implements NoteService{

    private final NoteMapper noteMapper;
    private final ProfileMapper profileMapper;
    private final MemberMapper memberMapper;

    /**
     * 노트 저장
     * @param noteDto
     * @throws Exception
     */
    @Override
    public void saveNote(NoteDto noteDto) throws Exception {

        //보내는 사람 노트 DB 저장
        Note note = noteDto.toEntity(noteDto);
        noteMapper.saveSenderNote(note);

        //받는 사람 노트 DB 저장
        noteMapper.saveReceiverNote(note);
    }


    /**
     * 노트 정보 가져오기
     * @param id
     * @return List<NoteDto>
     */
    @Override
    public List<NoteDto> getNote(Integer id) {
        //noteList 목록 가져오기
        List<Note> noteList = noteMapper.getNote(id);

        //noteList 목록을 담을 list객체 생성
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : noteList) {
            NoteDto noteDto = new NoteDto().toDto(note);

            //sender 프로필 사진 가져오기
            String senderPath = profileMapper.getImagePath(note.getSender_id());
            if(senderPath != null) {
                noteDto.setSender_img_path(senderPath);
            }

            //sender 닉네임 가져오기
            String senderNickName = memberMapper.findNickname(note.getSender_id());
            noteDto.setSender_nickname(senderNickName);

            noteDtos.add(noteDto);
        }

        return noteDtos;
    }

    /**
     * 노트 삭제
     * @param note_id
     * @throws Exception
     */
    @Override
    public void deleteNote(Integer note_id) throws Exception {
        noteMapper.deleteNote(note_id);
    }

}
