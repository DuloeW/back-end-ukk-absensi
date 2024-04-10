package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.util.Objects;
import java.util.stream.Collectors;

import org.acme.ukk.absensi.core.jwt.authentication.JwtTokenUtil;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.entity.enums.GradeEnum;
import org.acme.ukk.absensi.entity.enums.MajorEnum;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.ClassBody;
import org.acme.ukk.absensi.model.body.ClassBodyString;

@ApplicationScoped
public class ClassService {

  public Response getClassById(Long id, String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean scanner = JwtTokenUtil.validateToken(token, 2);
    boolean user = JwtTokenUtil.validateToken(token, 3);
    if (admin || user || scanner) {
      var entity = ClassEntity
          .findClassById(id)
          .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

      return Response
          .ok()
          .entity(ResponseJson.createJson(200, "Success", entity))
          .build();
    } else {
      return ResponseMessage.authorizationMessage();
    }
  }

  public Response getAllClass(String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean scanner = JwtTokenUtil.validateToken(token, 2);
    boolean user = JwtTokenUtil.validateToken(token, 3);

    if (admin || user || scanner) {
      var entity = ClassEntity
          .findAllClass()
          .stream()
          .collect(Collectors.toList());
      return Response
          .ok()
          .entity(ResponseJson.createJson(200, "Success", entity))
          .build();
    } else {
      return ResponseMessage.authorizationMessage();
    }
  }

  public Response getClassByMajorAndGrade(ClassBodyString body, String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean scanner = JwtTokenUtil.validateToken(token, 2);
    boolean user = JwtTokenUtil.validateToken(token, 3);
    if (admin || user || scanner) {
      var major = MajorEnum.getMajorEnum(body.major());
      var grade = GradeEnum.getGradeEnum(body.grade());
      var entity = ClassEntity.findClassByMajorAndGrade(major, grade)
          .orElseThrow(() -> ResponseMessage.classNotFoundException(body.grade(), body.major()));
      return Response.ok().entity(ResponseJson.createJson(200, "Success", entity)).build();
    } else {
      return ResponseMessage.authorizationMessage();
    }
  }

  public Response createNewClass(ClassBody body, String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    boolean admin = JwtTokenUtil.validateToken(token, 1);
    boolean scanner = JwtTokenUtil.validateToken(token, 2);
    boolean user = JwtTokenUtil.validateToken(token, 3);
    if (admin || user || scanner) { 
      Objects.requireNonNull(body);
      var entity = body.mapToClassEntity();
      entity.persist();
      return Response
          .ok()
          .entity(ResponseJson.createJson(200, "Success", entity))
          .build();
    } else {
      return ResponseMessage.authorizationMessage();
    }
  }
}
