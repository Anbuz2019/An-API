package com.anbuz.anapicommon.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class InterfaceInvokeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    Long interfaceId;

    Map<String, Object> requestParams;

}
