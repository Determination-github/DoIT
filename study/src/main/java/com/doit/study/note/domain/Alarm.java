package com.doit.study.note.domain;

public class Alarm {

    private String content;
    private String url;

    public Alarm() {

    }

    public Alarm(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {return url;}
}
