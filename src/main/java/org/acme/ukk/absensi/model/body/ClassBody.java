package org.acme.ukk.absensi.model.body;

import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.entity.enums.GradeEnum;
import org.acme.ukk.absensi.entity.enums.MajorEnum;

public record ClassBody(
    Integer grade,
    Integer major
) {

    public ClassEntity mapToClassEntity() {
        var entity = new ClassEntity();
        entity.grade = GradeEnum.getGradeEnum(grade);
        entity.major = MajorEnum.getMajorEnum(major);
        return entity;
    }
    
}
