package org.acme.ukk.absensi.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.service.QrService;

@Path("/api/v1/qr")
public class QrController {

  @Inject
  QrService qrService;

  @GET
  @Path("/download/{id}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response downloadQrCodes(@PathParam("id") Long id) {
    StreamingOutput stream = new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException {
        try {
          // Write the byte array to the output stream
          output.write(qrService.getAllQrImageBuffers(id));
        } finally {
          // Close the output stream
          output.close();
        }
      }
    };

    // Return the file as a response with appropriate headers
    return Response
      .ok(
        // ResponseJson.createJson(200, "Success", null)
        stream
      )
      .header(
        HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=qr-code.zip"
      )
      .build();
  }
}
