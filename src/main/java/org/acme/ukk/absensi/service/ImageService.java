package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.acme.ukk.absensi.core.jwt.authentication.JwtTokenUtil;
import org.acme.ukk.absensi.core.util.ResponseJson;
import org.acme.ukk.absensi.entity.ImageEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.acme.ukk.absensi.model.body.ImageBody;

@ApplicationScoped
//TODO generate image id response conflig
public class ImageService {

  public Response getImageById(Long id, String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    if (
      JwtTokenUtil.validateToken(token, 1) ||
      JwtTokenUtil.validateToken(token, 2) ||
      JwtTokenUtil.validateToken(token, 3)
    ) {
      var image = ImageEntity
        .findImageById(id)
        .orElseThrow(() -> ResponseMessage.idNotFoundException(id));
      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Success", image))
        .build();
    } else {
      return Response.status(401).build();
    }
  }

  private boolean checkingImageIsExist(Long id) {
    return ImageEntity.findImageById(id).isPresent();
  }

  public Response getAllImage(String authorizationHeader) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    if (
      JwtTokenUtil.validateToken(token, 1) ||
      JwtTokenUtil.validateToken(token, 2) ||
      JwtTokenUtil.validateToken(token, 3)
    ) {
      var images = ImageEntity
        .findAllImages()
        .stream()
        .collect(Collectors.toList());

      return Response
        .ok()
        .entity(ResponseJson.createJson(200, "Success", images))
        .build();
    } else {
      return Response.status(401).build();
    }
  }

  public Response uploudFile(String authorizationHeader, ImageBody body) {
    String token = JwtTokenUtil.convertToken(authorizationHeader);
    if (JwtTokenUtil.validateToken(token, 1)) {
      try {
        InputStream originalImageStream = body.file;
        BufferedImage originalImage = ImageIO.read(originalImageStream);

        // Atur ukuran yang diinginkan
        int scaledWidth = 300;
        int scaledHeight = 200;

        // Buat gambar baru dengan ukuran yang diinginkan
        BufferedImage resizedImage = new BufferedImage(
          scaledWidth,
          scaledHeight,
          originalImage.getType()
        );
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        ImageEntity imageEntity = body.mapToImageEntity();

        // Konversi BufferedImage menjadi byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String imageString = Base64.getEncoder().encodeToString(imageBytes);
        imageEntity.setFile(imageString);

        // Simpan ke dalam basis data
        imageEntity.persist();

        return Response
          .ok(
            ResponseJson.createJson(200, "Success Uplouded", imageEntity)
          )
          .build();
      } catch (IOException e) {
        return Response.status(401).build();
      }
    } else {
      return Response.status(401).build();
    }
  }

    public Response deleteImageById(Long id) {
      if(!checkingImageIsExist(id)) {
        return ResponseMessage.idNotFound(id);
      }
      ImageEntity.deleteById(id);
      return ResponseMessage.deleteSucces(id);
    }
}
