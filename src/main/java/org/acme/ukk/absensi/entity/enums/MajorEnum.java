package org.acme.ukk.absensi.entity.enums;

import lombok.Getter;

public enum MajorEnum {
    TATA_BOGA(0),
    REKAYASA_PERANGKAT_LUNAK(1),
    AKUTANSI(2),
    TABLE_MANAGER(3),
    DESAIN_KOMUNIKASI_VISUAL(4),
    KULINER(5),
    NOT_IDENTIFY(-1);

    @Getter
    public final Integer code;

    MajorEnum(Integer code) {
        this.code = code;
    }

    public static MajorEnum getMajorEnum(Integer code) {
        return switch(code) {
            case 0 -> MajorEnum.TATA_BOGA;
            case 1 -> MajorEnum.REKAYASA_PERANGKAT_LUNAK;
            case 2 -> MajorEnum.AKUTANSI;
            case 3 -> MajorEnum.TABLE_MANAGER;
            case 4 -> MajorEnum.DESAIN_KOMUNIKASI_VISUAL;
            case 5 -> MajorEnum.KULINER;
            default -> MajorEnum.NOT_IDENTIFY;
        };
    }
    public static MajorEnum getMajorEnum(String major) {
        return switch(major) {
            case "TATA BOGA" -> MajorEnum.TATA_BOGA;
            case "REKAYASA PERANGKAT LUNAK" -> MajorEnum.REKAYASA_PERANGKAT_LUNAK;
            case "AKUTANSI" -> MajorEnum.AKUTANSI;
            case "TABEL MANAGER" -> MajorEnum.TABLE_MANAGER;
            case "DESAIN KOMUNIKASI VISUAL" -> MajorEnum.DESAIN_KOMUNIKASI_VISUAL;
            case "KULINER" -> MajorEnum.KULINER;
            default -> MajorEnum.NOT_IDENTIFY;
        };
    }
}
