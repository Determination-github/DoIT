package com.doit.study.alarm.service;

import com.doit.study.alarm.domain.Alarm;
import com.doit.study.alarm.dto.AlarmDto;
import com.doit.study.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService{

    private final AlarmMapper alarmMapper;

    @Override
    public void saveAlarm(AlarmDto alarmDto) {
        log.info("alarmDto={}", alarmDto);

        //msg만들기
        String msg = alarmDto.makeMsg(alarmDto.getGubun(), alarmDto.getMessage());
        alarmDto.setMessage(msg);

        //Entity 전환
        Alarm alarm = alarmDto.toEntity(alarmDto);

        alarmMapper.saveAlarm(alarm);
    }

    @Override
    public List<AlarmDto> getAlarm(Integer id) {
        log.info("id={}", id);

        List<Alarm> alarmList = alarmMapper.getAlarm(id);

        List<AlarmDto> alarmDtos = new ArrayList<>();

        if(!alarmList.isEmpty()) {
            for (Alarm alarm : alarmList) {

                //DTO 전환환
               AlarmDto alarmDto = new AlarmDto().toDto(alarm);

                log.info("alarmDto = {}", alarmDto);

                alarmDtos.add(alarmDto);
            }
        }

        return alarmDtos;
    }

    @Override
    public void deleteAlarm(Integer id) {
        alarmMapper.deleteAlarm(id);
    }


}
