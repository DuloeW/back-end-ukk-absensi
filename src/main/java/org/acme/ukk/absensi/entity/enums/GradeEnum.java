package org.acme.ukk.absensi.entity.enums;

import lombok.Getter;

public enum GradeEnum {
  X(0),
  XI(1),
  XII(2),
  NOT_A_CLASS(-1);

  @Getter
  private final Integer code;

  GradeEnum(Integer code) {
    this.code = code;
  }

  public static GradeEnum getGradeEnum(Integer code) {
    return switch (code) {
      case 0 -> GradeEnum.X;
      case 1 -> GradeEnum.XI;
      case 2 -> GradeEnum.XII;
      default -> GradeEnum.NOT_A_CLASS;
    };
  }

}
