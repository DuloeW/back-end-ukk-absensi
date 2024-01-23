package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.model.body.AdminScannerBody;
import org.acme.ukk.absensi.service.AdminScannerService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/admin-scanner")
public class AdminScannerController {
    
    @Inject
    AdminScannerService adminScannerService;

    @POST
    @Path("/login")
    public Response login(AdminScannerBody body) {
        return adminScannerService.login(body);
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(AdminScannerBody body) {
        return adminScannerService.register(body);
    }
}
