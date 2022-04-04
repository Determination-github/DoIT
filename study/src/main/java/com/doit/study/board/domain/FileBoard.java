package com.doit.study.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class FileBoard {

    @NotEmpty
    private String file_id, study_id;
}
