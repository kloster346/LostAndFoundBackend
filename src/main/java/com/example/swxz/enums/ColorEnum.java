package com.example.swxz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorEnum {
    RED(1, "红色"),
    LIGHT_RED(2, "浅红色"),
    DARK_RED(3, "暗红色"),
    GREEN(4, "绿色"),
    LIGHT_GREEN(5, "浅绿色"),
    DARK_GREEN(6, "暗绿色"),
    BLUE(7, "蓝色"),
    LIGHT_BLUE(8, "浅蓝色"),
    DARK_BLUE(9, "深蓝色"),
    YELLOW(10, "黄色"),
    ORANGE(11, "橙色"),
    PURPLE(12, "紫色"),
    PINK(13, "粉红色"),
    BROWN(14, "棕色"),
    GREY(15, "灰色"),
    BLACK(16, "黑色"),
    WHITE(17, "白色"),
    OTHER(18, "其他");

    private final Integer code;
    private final String name;

    public static ColorEnum getByCode(Integer code) {
        for (ColorEnum color : ColorEnum.values()) {
            if (color.getCode().equals(code)) {
                return color;
            }
        }
        return OTHER;
    }
}