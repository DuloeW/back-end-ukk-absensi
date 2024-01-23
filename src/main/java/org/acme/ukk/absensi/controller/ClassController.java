package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.model.body.ClassBody;
import org.acme.ukk.absensi.service.ClassService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/class")
@Produces(MediaType.APPLICATION_JSON)
public class ClassController {

    @Inject
    ClassService classService;
    
    @GET
    @Path("/get/{id}")
    public Response getClassById(@PathParam("id") Long id) {
        return classService.getClassById(id);
    }

    @GET
    @Path("/get-all")
    public Response getAllClass() {
        return classService.getAllClass();
    }

    @POST
    @Path("/create")
    @Transactional
    public Response createNewClass(ClassBody body) {
        return classService.createNewClass(body);
    }
}