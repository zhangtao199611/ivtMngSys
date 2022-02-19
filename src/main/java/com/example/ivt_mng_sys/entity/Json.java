package com.example.ivt_mng_sys.entity;
import lombok.Data;
@Data
public class Json<T> {
    private int code;
    private T data;
}
