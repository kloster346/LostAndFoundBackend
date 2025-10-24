package com.example.swxz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BuildingEnum {
    XUEYOU_LOU(1, "学友楼"),
    XIAOYOU_LOU(2, "校友楼"),
    WENZONG_LOU(3, "文综楼"),
    YIFU_LOU(4, "逸夫楼"),
    XINGZHENG_LOU(5, "行政楼"),
    AI_COLLEGE(6, "人工智能学院"),
    WEN_COLLEGE(7, "文学院"),
    MATH_PHYSICS_COLLEGE(8, "数学与物理学院"),
    LAW_COLLEGE(9, "法学院"),
    PEOPLE_ARMED_COLLEGE(10, "人民武装学院"),
    SI_SLOP_CANTEEN(11, "四坡食堂"),
    WU_SLOP_CANTEEN(12, "五坡食堂"),
    BA_SLOP_CANTEEN(13, "八坡食堂"),
    DAFENG_COURT(14, "大丰球场"),
    TRACK_FIELD(15, "田径场"),
    GYM(16, "体育馆"),
    ROADSIDE(17, "路边"),
    LAKESIDE_FOUR_SLOP(18, "湖边（四坡）"),
    LAKESIDE_FIVE_SLOP(19, "湖边（五坡）"),
    SOUTH_GATE(20, "南门"),
    NORTH_GATE(21, "北门"),
    EAST_GATE(22, "东门"),
    SUBWAY_GATE(23, "地铁小门"),
    OTHER(24, "其他");

    private final Integer code;
    private final String name;

    public static BuildingEnum getByCode(Integer code) {
        for (BuildingEnum building : BuildingEnum.values()) {
            if (building.getCode().equals(code)) {
                return building;
            }
        }
        return OTHER;
    }
}