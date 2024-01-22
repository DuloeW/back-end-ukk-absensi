package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.model.body.AdminDashboardBody;
import org.acme.ukk.absensi.service.AdminDashboardService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/admin-dasboard")
public class AdminDashboardController {
    
    @Inject
    AdminDashboardService adminDashboardService;


    @POST
    @Path("/login")
    public Response login(AdminDashboardBody body) {
        return adminDashboardService.login(body);
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(AdminDashboardBody admin) {
        return adminDashboardService.register(admin);
    }
}
