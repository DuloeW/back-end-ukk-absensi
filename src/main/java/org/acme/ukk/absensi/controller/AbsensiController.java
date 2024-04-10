    package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.model.body.AbsensiBody;
import org.acme.ukk.absensi.service.AbsensiService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
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
    public Response getAllAbsensi(@HeaderParam("Authorization") String authorizationHeader) {
        return absensiService.getAllAbsensi(authorizationHeader);
    }

    @POST
    @Path("/get/status")
    public Response getAbsensiTodayByStatus( @HeaderParam("Authorization") String authorizationHeader, AbsensiBody body) {
        return absensiService.getAbsensiTodayByStatus(body, authorizationHeader);
    }

    @POST
    @Path("/get/date")
    public Response getAbsensiByDate( @HeaderParam("Authorization") String authorizationHeader, AbsensiBody body) {
        return absensiService.getAbsensiByDate(body, authorizationHeader);
    }

    @POST
    @Path("/create")
    @Transactional
    public Response createAbsensi( @HeaderParam("Authorization") String authorizationHeader, AbsensiBody body) {
        return absensiService.createAbsensi(body, authorizationHeader);
    }

    @PATCH
    @Path("/update")
    @Transactional
    public Response updateAbsensi( @HeaderParam("Authorization") String authorizationHeader, AbsensiEntity absensi) {
        return absensiService.updateAbsensi(absensi, authorizationHeader);
    }
}
