package com.example.excel.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestEnum {
    START((byte) 0, "START", null, false),
    BASIC_ACTION((byte) 1, "BA", "衍生规则", true),
    RULE_SET((byte) 2, "RS", "规则集", true);

    private Byte code;
    private String name;
    private String displayName;
    private Boolean isStrategy;

    public static TestEnum getEnumByCode(byte code) {
        for (TestEnum e : TestEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static TestEnum getEnumByName(String name) {
        for (TestEnum e : TestEnum.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
