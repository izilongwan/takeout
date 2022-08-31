package com.my_enum;

public enum GenderEnum {
    MAN(1, "男"),
    WOMAN(2, "女");

    Integer value;
    String desc;

    private GenderEnum() {
    }

    private GenderEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
