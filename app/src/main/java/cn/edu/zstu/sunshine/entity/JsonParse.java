package cn.edu.zstu.sunshine.entity;

import java.io.Serializable;

/**
 * Json解析的基类
 * Created by CooLoongWu on 2017-9-21 14:57.
 */

public class JsonParse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
