package com.doit.study.option.category;

import java.util.HashMap;
import java.util.Map;

public class Interest {

    private Map<String, String> interestMap = new HashMap<>();

    public void initMap() {
        interestMap.put("1", "개발");
        interestMap.put("2", "자격증");
        interestMap.put("3", "프로젝트");
        interestMap.put("4", "Back-End");
        interestMap.put("5", "Front-End");
        interestMap.put("6", "기사자격증");
        interestMap.put("7", "산업기사");
        interestMap.put("8", "백엔드");
        interestMap.put("9", "프론트엔드");
        interestMap.put("10", "풀스택");
    }

    public Map<String, String> getInterestMap() {
        initMap();
        return interestMap;
    }
}
