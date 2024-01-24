package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.model.body.AbsensiBody;
import org.acme.ukk.absensi.service.AbsensiService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/absensi")
public class AbsensiController {
    
    @Inject
    AbsensiService absensiService;

    @GET
    @Path("/get/{id}")
    public Response getAbsensiById(@PathParam("id") Long  id) {
        return absensiService.getAbsensiById(id);
    }

    @GET
    @Path("/get-all")
    public Response getAllAbsensi() {
        return absensiService.getAllAbsensi();
    }

    @POST
    @Path("/create")
    @Transactional
    public Response createAbsensi(AbsensiBody body) {
        return absensiService.createAbsensi(body);
    }
}
