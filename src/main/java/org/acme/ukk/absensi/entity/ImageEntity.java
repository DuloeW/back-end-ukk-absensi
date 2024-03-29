package org.acme.ukk.absensi.entity;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import static org.acme.ukk.absensi.core.util.ManipulateUtil.changeItOrNot;

@Table(name = "gambar")
@Entity
@Setter
public class ImageEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "bayu_id_gen")
    @Column(name = "id")
    @NotNull
    public Long id;


    @Column(name = "file", columnDefinition = "blob")
    @NotNull
    public String file;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    public StudentsEntity student;


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