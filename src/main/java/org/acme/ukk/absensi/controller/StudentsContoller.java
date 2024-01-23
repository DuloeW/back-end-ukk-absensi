package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.model.body.StudentsBody;
import org.acme.ukk.absensi.service.StudentsService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/students")
public class StudentsContoller {
    
    @Inject
    StudentsService studentsService;

    @GET
    @Path("/get/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        return studentsService.getStudentById(id);
    }

    @GET
    @Path("/get/status/active")
    public Response getStudenstActive() {
        return studentsService.getStudentsActive();
    }

    @GET
    @Path("/get-all")
    public Response getAllStudent() {
        return studentsService.getAllStudents();
    }

    @POST
    @Path("/create")
    @Transactional
    public Response createAllStudents(StudentsBody body) {
        return studentsService.createStudents(body);
    }
}
