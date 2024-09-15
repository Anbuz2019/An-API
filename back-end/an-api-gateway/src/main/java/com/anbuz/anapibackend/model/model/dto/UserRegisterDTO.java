package com.anbuz.anapibackend.model.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    private String userAccount;
    private String username;
    private String passwd;
    private String checkPasswd;
}
