package org.acme.ukk.absensi.model.body;

import java.time.LocalDate;
import java.time.LocalTime;

import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.entity.enums.AbsenEnum;

public record AbsensiBody(
    String student,
    LocalDate date,
    LocalTime time,
    Integer status
) {

    public AbsensiEntity mapToAbsensiEntity() {
        var absensi = new AbsensiEntity();
        absensi.time = LocalTime.now();
        absensi.status = AbsenEnum.getAbsenEnum(status);
        return absensi;
    }
    
}
