package com.anbuz.anapicommon.common;

import com.anbuz.anapicommon.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;
    private String description;

    public static <T> BaseResponse<T> success(){
        return new BaseResponse<>(0, "success", null, "");
    }

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, "success", data, "");
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), null, "");
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String msg){
        return new BaseResponse<>(errorCode.getCode(), msg, null, "");
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String msg, String description){
        return new BaseResponse<>(errorCode.getCode(), msg, null, description);
    }

    public static <T> BaseResponse<T> error(int code, String msg){
        return new BaseResponse<>(code, msg, null, "");
    }

    public static <T> BaseResponse<T> error(int code, String msg, String description){
        return new BaseResponse<>(code, msg, null, description);
    }
}
