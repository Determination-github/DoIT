package com.doit.study.alarm.service;

import com.doit.study.alarm.domain.Alarm;
import com.doit.study.alarm.dto.AlarmDto;
import com.doit.study.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AlarmServiceImpl implements AlarmService{

    private final AlarmMapper alarmMapper;

    /**
     * 알람 저장
     * @param alarmDto
     * @throws Exception
     */
    @Override
    public void saveAlarm(AlarmDto alarmDto) throws Exception {
        //알람 메시지 만들기
        String msg = alarmDto.makeMsg(alarmDto.getGubun(), alarmDto.getMessage());
        alarmDto.setMessage(msg);

        //Entity 전환
        Alarm alarm = alarmDto.toEntity(alarmDto);

        //알람 db 저장
        alarmMapper.saveAlarm(alarm);
    }

    /**
     * 알람 가져오기
     * @param id
     * @return List<AlarmDto>
     * @throws Exception
     */
    @Override
    public List<AlarmDto> getAlarm(Integer id) throws Exception {

        List<Alarm> alarmList = alarmMapper.getAlarm(id);

        //알람 메시지 저장 객체 생성
        List<AlarmDto> alarmDtos = new ArrayList<>();

        if(!alarmList.isEmpty()) { //알람 목록 저장
            for (Alarm alarm : alarmList) {

               //DTO 전환
               AlarmDto alarmDto = new AlarmDto().toDto(alarm);

               alarmDtos.add(alarmDto);
            }
        }

        return alarmDtos;
    }

    /**
     * 알람 메시지 삭제
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteAlarm(Integer id) throws Exception {
        alarmMapper.deleteAlarm(id);
    }


}
