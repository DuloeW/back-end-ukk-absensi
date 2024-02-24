package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.entity.enums.AbsenEnum;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.AbsensiBody;

@ApplicationScoped
public class AbsensiService {

  private AbsensiEntity getAbsensiCoke (Long id) {
    return AbsensiEntity
    .findAbsenById(id)
    .orElseThrow(() -> ResponseMessage.idNotFoundException(id));
  }

  public StudentsEntity fetchStudentEntity(Long id) {
    return StudentsEntity
      .findStudentsById(id)
      .orElseThrow(() -> ResponseMessage.fetchMessageException(id, "students"));
  }

  public StudentsEntity fetchStudentsEntityByNisn(String nisn) {
    return StudentsEntity
      .findStudentByNisn(nisn)
      .orElseThrow(() -> ResponseMessage.nisnNotFoundException(nisn));
  }

  public Response getAbsensiById(Long id) {
    var absensi = AbsensiEntity
      .findAbsenById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Berhasil", absensi))
      .build();
  }

  public Response getAllAbsensi() {
    var absensi = AbsensiEntity
      .findAllAbsen()
      .stream()
      .collect(Collectors.toList());

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Berhasil", absensi))
      .build();
  }

  public Response getAbsensiTodayByStatus(AbsensiBody body) {
    var absensi = AbsensiEntity
      .findAbsensiTodayByStatus(
        AbsenEnum.getAbsenEnum(body.status()),
        body.date()
      )
      .stream()
      .collect(Collectors.toList());
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", absensi))
      .build();
  }

  public Response getAbsensiByDate(AbsensiBody body) {
    var absensi = AbsensiEntity
      .findAbsensiByDate(body.date())
      .stream()
      .collect(Collectors.toList());
    
      return Response.ok(absensi).build();
  }

  private AbsensiEntity createRelasi(AbsensiBody body, StudentsEntity student) {
    var absensi = body.mapToAbsensiEntity();
    absensi.student = student;
    absensi.persist();
    return absensi;
  }

  public Response createAbsensi(AbsensiBody body) {
    Objects.requireNonNull(body);
    var student = fetchStudentsEntityByNisn(body.student());
    boolean absenIsExist = student.absensi
      .stream()
      .anyMatch(absen -> absen.date.equals(LocalDate.now()));
    if (!absenIsExist) {
      var absensi = createRelasi(body, student);
      student.persist();
      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Berhasil Absensi", absensi))
        .build();
    } else {
      return Response
        .status(Status.BAD_REQUEST)
        .entity(
          ResponseJson.createJson(400, "Siswa Sudah Absen Hari Ini!!", null)
        )
        .build();
    }
  }

  public Response updateAbsensi(AbsensiEntity entity) {
    var absensi = getAbsensiCoke(entity.id);
    entity.updateAbsensi(absensi);
    return Response.ok(absensi).build();
  }
}
