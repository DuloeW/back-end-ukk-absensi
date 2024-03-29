package org.acme.ukk.absensi.model.body;

import java.io.InputStream;
import java.util.UUID;

import org.acme.ukk.absensi.entity.ImageEntity;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

public class ImageBody {
    
    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    public ImageEntity mapToImageEntity() {
        return new ImageEntity();
    }
}
