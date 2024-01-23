package org.acme.ukk.absensi.service;

import io.quarkus.vertx.http.runtime.devmode.Json;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Objects;
import org.acme.ukk.absensi.core.jwt.TokenResponse;
import org.acme.ukk.absensi.core.jwt.authentication.JwtTokenUtil;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.UserEntity;
import org.acme.ukk.absensi.model.body.UserBody;

@ApplicationScoped
public class UserService {

  public Response login(UserBody body) {
    var user = UserEntity.findUserByEmailAndPass(body.email(), body.password());
    if (user.isPresent()) {
      String token = JwtTokenUtil.generateUserToken(body.email());
      return Response
        .ok()
        .entity(new TokenResponse(token, "Success Login"))
        .build();
    } else {
      return Response
        .status(Status.BAD_REQUEST)
        .entity("{\"message\" : \"Login Failed\"}")
        .build();
    }
  }

  public Response register(UserBody body) {
    Objects.requireNonNull(body);
    var user = body.mapToUserEntity();
    user.persist();
    return Response
      .ok()
      .entity(ResponseJson.createJson(200, "Success", user))
      .build();
  }
}
