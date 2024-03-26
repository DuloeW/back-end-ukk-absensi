package org.acme.ukk.absensi.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.model.body.StudentsBody;
import org.acme.ukk.absensi.service.StudentsService;

@Path("/api/v1/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentsContoller {

  @Inject
  StudentsService studentsService;

  @GET
  @Path("/get/{id}")
  public Response getStudentById(
    @HeaderParam("Authorization") String authorizationHeader,
    @PathParam("id") Long id
  ) {
    return studentsService.getStudentById(authorizationHeader, id);
  }

  @GET
  @Path("/get/status/active")
  public Response getStudenstActive(
    @HeaderParam("Authorization") String authorizationHeader
  ) {
    return studentsService.getStudentsActive(authorizationHeader);
  }

  @GET
  @Path("/get/nisn/{nisn}")
  public Response getStudentByNisn(
    @HeaderParam("Authorization") String authorizationHeader,
    @PathParam("nisn") String nisn
  ) {
    return studentsService.getStudentByNisn(authorizationHeader, nisn);
  }

  @GET
  @Path("/get/name/{name}")
  public Response getStudentsByName(@PathParam("name") String name) {
    return studentsService.getStudentsByName(name);
  }

  @GET
  @Path("/get-all")
  public Response getAllStudent(
    @HeaderParam("Authorization") String authorizationHeader
  ) {
    return studentsService.getAllStudents(authorizationHeader);
  }

  @POST
  @Path("/create")
  @Transactional
  public Response createAllStudents(
    @HeaderParam("Authorization") String authorizationHeader,
    StudentsBody body
  ) {
    return studentsService.createStudents(authorizationHeader, body);
  }

  @GET
  @Path("/get/class/{grade}/{major}")
  public Response getStudetsByClass(
    @PathParam("grade") String grade,
    @PathParam("major") String major,
    @HeaderParam("Authorization") String authorizationHeader
  ) {
    return studentsService.getStudetsByClass(grade, major, authorizationHeader);
  }

  @PUT
  @Path("/update")
  @Transactional
  public Response updateStudent(
    @HeaderParam("Authorization") String authorizationHeader,
    StudentsEntity entity
  ) {
    return studentsService.updateStudent(authorizationHeader, entity);
  }
}
