package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.util.Objects;
import java.util.stream.Collectors;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.ClassBody;

@ApplicationScoped
public class ClassService {

  public Response getClassById(Long id) {
    var entity = ClassEntity
      .findClassById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", entity))
      .build();
  }

  public Response getAllClass() {
    var entity = ClassEntity
      .findAllClass()
      .stream()
      .collect(Collectors.toList());
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", entity))
      .build();
  }

  public Response createNewClass(ClassBody body) {
    Objects.requireNonNull(body);
    var entity = body.mapToClassEntity();
    entity.persist();
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", entity))
      .build();
  }
}
