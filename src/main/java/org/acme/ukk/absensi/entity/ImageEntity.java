package org.acme.ukk.absensi.entity;

import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import static org.acme.ukk.absensi.core.util.ManipulateUtil.changeItOrNot;

@Table(name = "gambar")
@Entity
@Setter
public class ImageEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    @NotNull
    public Long id;


    @Column(name = "file", columnDefinition = "blob")
    @NotNull
    public String file;


    public static Optional<ImageEntity> findImageById(Long id) {
        return find("id =? 1",id).firstResultOptional();
    }

    public static List<ImageEntity> findAllImages() {
        return ImageEntity.listAll();
    }

    public ImageEntity updateImageEntity(ImageEntity image) {
        image.file = changeItOrNot(file, image.file);
        return image;
    }
    
}