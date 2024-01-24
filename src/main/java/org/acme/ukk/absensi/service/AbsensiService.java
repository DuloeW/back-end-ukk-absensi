package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.util.Objects;
import java.util.stream.Collectors;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.AbsensiBody;

@ApplicationScoped
public class AbsensiService {

  public StudentsEntity fetchStudentEntity(Long id) {
    return StudentsEntity
      .findStudentsById(id)
      .orElseThrow(() -> ResponseMessage.fetchMessageException(id, "students"));
  }

  public Response getAbsensiById(Long id) {
    var absensi = AbsensiEntity
      .findAbsenById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", absensi))
      .build();
  }

  public Response getAllAbsensi() {
    var absensi = AbsensiEntity
      .findAllAbsen()
      .stream()
      .collect(Collectors.toList());

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", absensi))
      .build();
  }

  private AbsensiEntity createRelasi(AbsensiBody body, StudentsEntity student) {
    var absensi = body.mapToAbsensiEntity();
    absensi.student = student;
    absensi.persist();
    return absensi;
  }

  public Response createAbsensi(AbsensiBody body) {
    Objects.requireNonNull(body);
    var student = fetchStudentEntity(body.student());
    var absensi = createRelasi(body, student);
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", absensi))
      .build();
  }
}
