package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Objects;
import org.acme.ukk.absensi.core.jwt.TokenResponse;
import org.acme.ukk.absensi.core.jwt.authentication.JwtTokenUtil;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.AdminScannerEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.AdminScannerBody;

@ApplicationScoped
public class AdminScannerService {

  public Response getAdminScannerById(Long id) {
    var admin = AdminScannerEntity
      .findAdminScannerById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));

    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", admin))
      .build();
  }

  public Response login(AdminScannerBody body) {
    var admin = AdminScannerEntity.findAdminByEmailAndPass(
      body.email(),
      body.password()
    );

    if (admin.isPresent()) {
      String token = JwtTokenUtil.generateScannerToken(body.email());
      return Response
        .ok()
        .entity(new TokenResponse(token, "Seccess Login"))
        .build();
    }
    return Response
      .status(Status.BAD_REQUEST)
      .entity("{\"message\" : \"Login Failed\"}")
      .build();
  }

  public Response register(AdminScannerBody body) {
    Objects.requireNonNull(body);
    var admin = body.mapToAdminScannerEntity();
    admin.persist();
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", admin))
      .build();
  }
}
