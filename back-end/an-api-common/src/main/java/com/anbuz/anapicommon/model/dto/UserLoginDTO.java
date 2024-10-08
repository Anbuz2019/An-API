package com.anbuz.anapicommon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserLoginDTO {
    private String userAccount;
    private String userPassword;
}
