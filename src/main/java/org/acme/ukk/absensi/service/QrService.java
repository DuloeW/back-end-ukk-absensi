package org.acme.ukk.absensi.service;

import io.nayuki.fastqrcodegen.QrCode;
import io.nayuki.fastqrcodegen.QrCodeGeneratorDemo;
import jakarta.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.entity.StudentsEntity;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.json.JSONObject;

@ApplicationScoped
public class QrService {

  public ClassEntity fetchClass(Long id) {
    return ClassEntity
      .findClassById(id)
      .orElseThrow(() -> ResponseMessage.idNotFoundException(id));
  }

  public byte[] getAllQrImageBuffers(Long id) {
    var classGrade = fetchClass(id);

    try (
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ZipOutputStream zos = new ZipOutputStream(baos)
    ) {
      classGrade.students.forEach(student -> {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nisn", student.nisn);

        QrCode qr = QrCode.encodeText(jsonObject.toString(), QrCode.Ecc.MEDIUM);
        BufferedImage image = QrCodeGeneratorDemo.toImage(qr, 20, 4);
        byte[] imageBuf = convertToByteArray(image);

        try {
          addToZip(
            student.name.toLowerCase().replace(" ", "_") +
            "_" +
            student.classGrade.major.toString().toLowerCase() +
            ".png",
            imageBuf,
            zos
          );
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      // Finish writing to the zip file
      zos.finish();

      // Get the byte array from the ByteArrayOutputStream
      return baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private static byte[] convertToByteArray(BufferedImage image) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      // Write the image data to the ByteArrayOutputStream
      ImageIO.write(image, "png", baos);

      // Get the byte array from the ByteArrayOutputStream
      return baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private static void addToZip(
    String fileName,
    byte[] buffer,
    ZipOutputStream zos
  ) throws IOException {
    try (ByteArrayOutputStream bis = new ByteArrayOutputStream()) {
      ZipEntry zipEntry = new ZipEntry(fileName);
      zos.putNextEntry(zipEntry);

      // Write the buffer to the zip file
      zos.write(buffer, 0, buffer.length);

      zos.closeEntry();
    }
  }
}
