package com.example.excel.config;

public enum RestCode {
    SUCCESS(200, "OK"),
    SPRING(1,"春天"),
    SUMMER(2,"夏天"),
    FALL(3,"秋天"),
    WINTER(4,"冬天");

    private final Integer code;
    private final String msg;

    RestCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public static void main(String[] args) {
        Integer code = RestCode.SPRING.getCode();
        System.out.println(code);
        RestCode value = RestCode.valueOf(RestCode.class, "SPRING");
        System.out.println(value.getCode() + "--" + value.getMsg());
        for (RestCode c : RestCode.values()){
            System.out.println(c.getCode());
        }
        System.out.println(RestCode.WINTER.compareTo(RestCode.SPRING));
        System.out.println(RestCode.WINTER.name());
        System.out.println(RestCode.WINTER.ordinal());
    }

}
