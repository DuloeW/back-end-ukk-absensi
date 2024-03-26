package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.acme.ukk.absensi.core.jwt.authentication.JwtTokenUtil;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.entity.ImageEntity;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.entity.enums.GradeEnum;
import org.acme.ukk.absensi.entity.enums.MajorEnum;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.StudentsBody;

@ApplicationScoped
public class StudentsService {

  private StudentsEntity getStudentById(Long id) {
    return StudentsEntity
      .findStudentsById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));
  }

  private boolean checkingIsStudentExist(Long id) {
    return StudentsEntity.findStudentsById(id).isPresent();
  }

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

  private boolean checkingIsStudentExist(String nisn) {
    return StudentsEntity.findStudentByNisn(nisn).isPresent();
  }

  public Response getStudentById(String authorizationHeader, Long id) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean user = JwtTokenUtil.validateToken(token, 3);

    if (admin || user) {
      var student = StudentsEntity
        .findStudentsById(id)
        .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Success", student))
        .build();
    } else {
      return Response
        .status(Status.UNAUTHORIZED)
        .entity(ResponseJson.createJson(401, "Token has exp", null))
        .build();
    }
  }

  public Response getStudentByNisn(String authorizationHeader, String nisn) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean scanner = JwtTokenUtil.validateToken(token, 2);
    boolean user = JwtTokenUtil.validateToken(token, 3);

    if (scanner || user) {
      var student = StudentsEntity
        .findStudentByNisn(nisn)
        .orElseThrow(() -> ResponseMessage.nisnNotFoundException(nisn));
      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Success", student))
        .build();
    } else {
      return Response
        .status(Status.UNAUTHORIZED)
        .entity(ResponseJson.createJson(401, "Token has exp", null))
        .build();
    }
  }

  public Response getStudentsActive(String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean scanner = JwtTokenUtil.validateToken(token, 2);
    boolean user = JwtTokenUtil.validateToken(token, 3);

    if (admin || scanner || user) {
      var students = StudentsEntity
        .findStudentsActive()
        .stream()
        .collect(Collectors.toList());

      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Success", students))
        .build();
    } else {
      return Response
        .status(Status.UNAUTHORIZED)
        .entity(ResponseJson.createJson(401, "Token has exp", null))
        .build();
    }
  }

  public Response getAllStudents(String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean scanner = JwtTokenUtil.validateToken(token, 1);
    boolean user = JwtTokenUtil.validateToken(token, 3);

    if (admin || scanner || user) {
      var students = StudentsEntity
        .findAllStudents()
        .stream()
        .collect(Collectors.toList());
      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Success", students))
        .build();
    } else {
      return Response
        .status(Status.UNAUTHORIZED)
        .entity(ResponseJson.createJson(401, "Token has exp", null))
        .build();
    }
  }

  public Response getStudentsByName(String name) {
    var students = StudentsEntity
      .findStudentByName(name.toLowerCase())
      .stream()
      .collect(Collectors.toList());
    return Response
      .ok(ResponseJson.createJson(200, "Success", students))
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

  public Response createStudents(
    String authorizationHeader,
    StudentsBody body
  ) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);

    if (admin) {
      Objects.requireNonNull(body);
      if (!checkingIsStudentExist(body.nisn())) {
        var classGrade = fetchClassEntity(body.classGrade());
        var image = fetchImageEntity(body.image());
        var student = createRelasi(body, classGrade, image);
        return Response
          .ok()
          .entity(ResponseJson.createJson(200, "Succes", student))
          .build();
      } else {
        return Response
          .status(Status.BAD_REQUEST)
          .entity(ResponseJson.createJson(400, "Student Is Exist", null))
          .build();
      }
    } else {
      return Response
        .status(Status.UNAUTHORIZED)
        .entity(ResponseJson.createJson(401, "Token has exp", null))
        .build();
    }
  }


  public Response getStudetsByClass(
    String grade,
    String major,
    String authorizationHeader
  ) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 3);
    GradeEnum gradeEnum = GradeEnum.getGradeEnum(grade);
    MajorEnum majorEnum = MajorEnum.getMajorEnum(major);
    if (admin) {
      var students = StudentsEntity
        .findAllStudentByClass(gradeEnum, majorEnum)
        .stream()
        .collect(Collectors.toList());
      return Response
        .ok(ResponseJson.createJson(200, "Success", students))
        .build();
    } else {
      return Response
        .status(Status.UNAUTHORIZED)
        .entity(ResponseJson.createJson(401, "Token has exp", null))
        .build();
    }
  }


  private StudentsEntity checkingAndUpdateRelation(StudentsEntity entity, ClassEntity classGrade, ImageEntity image) {
    var student = getStudentById(entity.id);
    entity.classGrade = classGrade;
    entity.image = image;
    entity.updateStudent(student);
    return entity;
  }

  //TOOO error update
  public Response updateStudent(
    String authorizationHeader,
    StudentsEntity entity
  ) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    if (JwtTokenUtil.validateToken(token, 1)) {
      if (checkingIsStudentExist(entity.id)) {
        var classGrade = fetchClassEntity(entity.classGrade.id);
        var image = fetchImageEntity(entity.image.id);
        var student = checkingAndUpdateRelation(entity, classGrade, image);
        return Response
          .ok()
          .entity(ResponseJson.createJson(200, "Success", student))
          .build();
      } else {
        return Response.status(400).build();
      }
    } else {
      return Response.status(401).build();
    }
  }
}
