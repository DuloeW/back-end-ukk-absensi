package org.acme.ukk.absensi.core.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseJson<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseJson<T> createJson(int code, String message, T data) {
        ResponseJson<T> response = new ResponseJson<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}