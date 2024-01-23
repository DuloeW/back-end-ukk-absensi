package org.acme.ukk.absensi.entity.enums;

import lombok.Getter;

public enum StudentStatusEnum {
    ACTIVE(0),
    NON_ACTIVE(1);

    @Getter
    public final Integer code;

    StudentStatusEnum(Integer code) {
        this.code = code;
    }

    public static StudentStatusEnum getStudentStatus(Integer code) {
        return switch(code) {
            case 0 -> StudentStatusEnum.ACTIVE;
            case 1 -> StudentStatusEnum.NON_ACTIVE;
            default -> StudentStatusEnum.NON_ACTIVE; 
        };
    }
}
