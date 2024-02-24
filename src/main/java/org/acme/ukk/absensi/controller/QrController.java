package org.acme.ukk.absensi.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.model.body.QrBody;
import org.acme.ukk.absensi.service.QrService;

@Path("/api/v1/qr")
public class QrController {

  @Inject
  QrService qrService;

  @POST
  @Path("/download")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response downloadQrCodes(QrBody body) {
    StreamingOutput stream = new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException {
        try {
          // Write the byte array to the output stream
          output.write(qrService.getAllQrImageBuffers(body));
        } finally {
          // Close the output stream
          output.close();
        }
      }
    };

    // Return the file as a response with appropriate headers
    return Response
      .ok((Object) stream)
      .header(
        HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=" + body.grade() + body.major()  + ".zip"
      )
      .build();
  }

  @GET
  @Path("/download/v2/{grade}/{major}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response downloadQrCodes(@PathParam("grade")String grade, @PathParam("major")String major) {
    StreamingOutput stream = new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException {
        try {
          // Write the byte array to the output stream
          output.write(qrService.getAllQrImageBuffers(grade, major));
        } finally {
          // Close the output stream
          output.close();
        }
      }
    };

    // Return the file as a response with appropriate headers
    return Response
            .ok((Object) stream)
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + grade + major  + ".zip"
            )
            .build();
  }
}
