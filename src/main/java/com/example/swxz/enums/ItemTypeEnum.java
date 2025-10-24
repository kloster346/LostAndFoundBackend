package com.example.swxz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemTypeEnum {
    BOOK(1, "书"),
    BAG(2, "包"),
    CARD(3, "卡"),
    KEY(4, "钥匙"),
    PHONE(5, "手机"),
    WATCH(6, "手表"),
    PEN(7, "笔"),
    UMBRELLA(8, "伞"),
    EARPHONE(9, "耳机"),
    OTHER(10, "其他");

    private final Integer code;
    private final String name;

    public static ItemTypeEnum getByCode(Integer code) {
        for (ItemTypeEnum type : ItemTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return OTHER;
    }
}