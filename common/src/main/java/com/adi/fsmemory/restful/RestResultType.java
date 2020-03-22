package com.adi.fsmemory.restful;

public enum RestResultType {
    SUCCESS(200, "success"),
    ERROR(500, "error"),
    UN_KNOWN_ACCOUNT(-1,"找不到该用户"),
    INCORRECT_CREDENTIALS(-2,"用户密码不正确"),
    UN_AUTHENTICATED(-3,"用户还未进行身份验证"),
    WAIT_FOR_CHECK(-4,"待审核"),
    SEARCH_ID_REPETITION(-21,"组织编号重复");

    private int code;
    private String message;

    RestResultType() {
    }

    RestResultType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 直接将枚举类型转为结果返回
      * @param data
     * @return
     */
    public RestResultObj toRestfulResult(Object data) {
        return RestResultObj.toRestResultObj(this, data);
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
}
