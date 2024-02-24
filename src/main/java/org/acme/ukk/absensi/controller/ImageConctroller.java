package org.acme.ukk.absensi.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import org.acme.ukk.absensi.model.body.ImageBody;
import org.acme.ukk.absensi.service.ImageService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;


@Path("api/v1/image")
@Produces(MediaType.APPLICATION_JSON)
public class ImageConctroller {

  @Inject
  ImageService imageService;

  @GET
  @Path("/get/{id}")
  public Response getImageById(@HeaderParam("Authorization") String authorizationHeader, @PathParam("id") Long id) {
    return imageService.getImageById(id, authorizationHeader);
  }

  @GET
  @Path("/get-all")
  public Response getAllImage(@HeaderParam("Authorization") String authorizationHeader) {
    return imageService.getAllImage(authorizationHeader);
  }

  @POST
  @Path("/uploud")
  @Transactional
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response uploudImage(@HeaderParam("Authorization") String authorizationHeader, @MultipartForm ImageBody body) {
    return imageService.uploudFile(authorizationHeader, body);
  }

  @DELETE
  @Path("/delete/{id}")
  @Transactional
  public Response deleteImageById(@PathParam("id")Long id) {
    return imageService.deleteImageById(id);
  }
}
