    package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.model.body.AbsensiBody;
import org.acme.ukk.absensi.service.AbsensiService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

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
    @Path("/get/status")
    public Response getAbsensiTodayByStatus(AbsensiBody body) {
        return absensiService.getAbsensiTodayByStatus(body);
    }

    @POST
    @Path("/get/date")
    public Response getAbsensiByDate(AbsensiBody body) {
        return absensiService.getAbsensiByDate(body);
    }

    @POST
    @Path("/create")
    @Transactional
    public Response createAbsensi(AbsensiBody body) {
        return absensiService.createAbsensi(body);
    }

    @PATCH
    @Path("/update")
    @Transactional
    public Response updateAbsensi(AbsensiEntity absensi) {
        return absensiService.updateAbsensi(absensi);
    }
}
