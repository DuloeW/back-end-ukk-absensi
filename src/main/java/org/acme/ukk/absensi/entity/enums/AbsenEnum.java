package org.acme.ukk.absensi.entity.enums;

import lombok.Getter;

public enum AbsenEnum {
  HADIR(0),
  IZIN(1),
  SAKIT(2),
  ALPA(-1);

  @Getter
  public final Integer code;

  AbsenEnum(Integer code) {
    this.code = code;
  }

  public static AbsenEnum getAbsenEnum(Integer code) {
    return switch (code) {
      case 0 -> AbsenEnum.HADIR;
      case 1 -> AbsenEnum.IZIN;
      case 2 -> AbsenEnum.SAKIT;
      default -> AbsenEnum.ALPA;
    };
  }
}
