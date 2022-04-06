package com.doit.study.option.location;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Address {

    private Map<String, String> addressMap = new HashMap<>();

    public void initMap() {
        addressMap.put("1", "서울특별시");
        addressMap.put("2", "부산광역시");
        addressMap.put("3", "대구광역시");
        addressMap.put("4", "인천광역시");
        addressMap.put("5", "광주광역시");
        addressMap.put("6", "대전광역시");
        addressMap.put("7", "울산광역시");
        addressMap.put("8", "강원도");
        addressMap.put("9", "경기도");
        addressMap.put("10", "경상남도");
        addressMap.put("11", "경상북도");
        addressMap.put("12", "전라남도");
        addressMap.put("13", "전라북도");
        addressMap.put("14", "충청남도");
        addressMap.put("15", "충청북도");
        addressMap.put("16", "제주도");
    }

    public Map<String, String> getAddressMap() {
        initMap();
        return addressMap;
    }


}
