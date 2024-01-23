package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.Objects;
import java.util.stream.Collectors;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.entity.ImageEntity;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.StudentsBody;

@ApplicationScoped
public class StudentsService {

  private ClassEntity fetchClassEntity(Long id) {
    return ClassEntity
      .findClassById(id)
      .orElseThrow(() -> ResponseMessage.fetchMessageException(id, "classGarde")
      );
  }

  private ImageEntity fetchImageEntity(Long id) {
    return ImageEntity
      .findImageById(id)
      .orElseThrow(() -> ResponseMessage.fetchMessageException(id, "image"));
  }

  public Response getStudentById(Long id) {
    var student = StudentsEntity
      .findStudentsById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", student))
      .build();
  }

  public Response getStudentsActive() {
    var students = StudentsEntity
      .findStudentsActive()
      .stream()
      .collect(Collectors.toList());

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", students))
      .build();
  }

  public Response getAllStudents() {
    var students = StudentsEntity
      .findAllStudents()
      .stream()
      .collect(Collectors.toList());
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", students))
      .build();
  }

  private StudentsEntity createRelasi(
    StudentsBody body,
    ClassEntity classGrade,
    ImageEntity image
  ) {
    var student = body.mapToStudentsEntity();
    student.classGrade = classGrade;
    student.image = image;
    student.persist();
    return student;
  }

  public Response createStudents(StudentsBody body) {
    Objects.requireNonNull(body);
    var classGrade = fetchClassEntity(body.classGrade());
    var image = fetchImageEntity(body.image());
    var student = createRelasi(body, classGrade, image);
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Succes", student))
      .build();
  }
}
