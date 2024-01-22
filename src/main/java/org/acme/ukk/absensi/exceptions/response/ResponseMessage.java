package org.acme.ukk.absensi.exceptions.response;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Gunakan exception ini jika malas bangod ngetik.
 *
 * @author Bayu , Gus Anta
 * @since 26 Des 2022
 */

public class ResponseMessage {

  private ResponseMessage() {}

  public static WebApplicationException fetchMessageException(
    Long id,
    String object
  ) {
    return new WebApplicationException(
      Response
        .status(400)
        .entity(
          "{\"field\":\"" +
          object +
          "\",  \n  \t\"id\": " +
          id +
          ",\n  \t\"message\":\"NOT FOUND OR FORMAT NOT VALID\"}"
        )
        .build()
    );
  }

  public static Response deleteSucces(Long id) {
    return Response
      .status(200)
      .entity("{\"id\": " + id + ",\n  \t\"message\":\"SUCCES DELETED\"}")
      .build();
  }

  public static Response idNotFound(Long id) {
    return Response
      .status(404)
      .entity("{\"id\": " + id + ",\n  \t\"message\":\"NOT FOUND\"}")
      .build();
  }

  public static WebApplicationException idNotFoundException(Long id) {
    return new WebApplicationException(
      Response
        .status(404)
        .entity("{\"id\": " + id + ",\n  \t\"message\":\"NOT FOUND\"}")
        .build()
    );
  }

  public static WebApplicationException loginFaild(
    String email,
    String pass,
    String code
  ) {
    return new WebApplicationException(
      Response
        .status(400)
        .entity(
          "{\"email\": " +
          email +
          ",\n  \t\"password\": " +
          pass +
          " \t\"kode\":" +
          code +
          "\" \t\"message\":\"NOT FOUND\"}"
        )
        .build()
    );
  }

  public static Response authorizationMessage() {
    return Response
      .status(Status.UNAUTHORIZED)
      .entity("{\"message\":\"Your token is not valid\"}")
      .build();
  }
}