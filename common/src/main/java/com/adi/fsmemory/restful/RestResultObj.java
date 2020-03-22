package com.adi.fsmemory.restful;

public class RestResultObj {

    private int code;
    private String message;
    private Object data;

    public RestResultObj() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RestResultObj(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RestResultObj toRestResultObj(RestResultType restResultType, Object data) {
        RestResultObj obj = new RestResultObj();
        obj.setCode(restResultType.getCode());
        obj.setMessage(restResultType.getMessage());
        obj.setData(data);
        return obj;
    }
}
