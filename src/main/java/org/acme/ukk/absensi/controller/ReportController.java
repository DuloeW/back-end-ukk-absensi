package org.acme.ukk.absensi.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.service.ReportService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Path("/api/v1/report")
public class ReportController {

    @Inject
    ReportService reportService;


    @GET
    @Path("/generate/{major}/{grade}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateReport(@PathParam("major") String major, @PathParam("grade") String grade) {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                try {
                    outputStream.write(reportService.generateReport(major, grade));
                } finally {
                    outputStream.close();
                }
            }
        };
        return Response.ok((Object) stream)
                .header("Content-Disposition", "attachment; filename=report.xlsx")
                .build();
    }
}
