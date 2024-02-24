package org.acme.ukk.absensi.model.body;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.acme.ukk.absensi.core.util.jackson.DateDeserialize;
import org.acme.ukk.absensi.core.util.jackson.TimeDeserialize;
import org.acme.ukk.absensi.core.util.jackson.TimeSerialize;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.entity.enums.StudentStatusEnum;

public record StudentsBody(
  String nisn,
  String name,
  Long classGrade,
  Long image,
  @JsonDeserialize(converter = DateDeserialize.class)
  LocalDate dateOfBirth,
  Integer status
) {

    public StudentsEntity mapToStudentsEntity() {
        var student = new StudentsEntity();
        student.nisn = nisn;
        student.name = name;
        student.dateOfBirth = dateOfBirth;
        student.status = StudentStatusEnum.getStudentStatus(status);
        return student;
    }
}
