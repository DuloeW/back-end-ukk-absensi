package org.acme.ukk.absensi.controller;

import org.acme.ukk.absensi.model.body.UserBody;
import org.acme.ukk.absensi.service.UserService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @POST
    @Path("/login")
    public Response login(UserBody body) {
        return userService.login(body);
    }

    @POST
    @Path("register")
    @Transactional
    public Response register(UserBody body) {
        return userService.register(body);
    }
}
