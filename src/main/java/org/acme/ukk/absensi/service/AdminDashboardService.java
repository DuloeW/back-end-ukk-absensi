package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Objects;

import org.acme.ukk.absensi.core.jwt.TokenResponse;
import org.acme.ukk.absensi.core.jwt.authentication.JwtTokenUtil;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.AdminDasboardEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.AdminDashboardBody;

@ApplicationScoped
public class AdminDashboardService {

  public Response getAdminDashboardById(Long id) {
    var admin = AdminDasboardEntity
      .findAdminDasboardById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", admin))
      .build();
  }

  public Response login(AdminDashboardBody body) {
    var admin = AdminDasboardEntity.findAdminDasboardByEmailAndPass(
      body.email(),
      body.password()
    );
    if (admin.isPresent()) {
      String token = JwtTokenUtil.generateAdminToken(body.email());
      return Response.ok().entity(new TokenResponse(token, "Success login")).build();
    } else {
      return Response
        .status(Status.BAD_REQUEST)
        .entity("{\"message\" : \"Login Failed\"}")
        .build();
    }
  }

  public Response register(AdminDashboardBody body) {
    Objects.requireNonNull(body);
    var admin = body.mapToDasboardEntity();
    admin.persist();
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", admin))
      .build();
  }
}
