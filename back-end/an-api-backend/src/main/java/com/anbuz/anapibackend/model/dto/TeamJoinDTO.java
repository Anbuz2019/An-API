package com.anbuz.anapibackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinDTO {

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
