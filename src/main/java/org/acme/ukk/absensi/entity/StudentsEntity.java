package org.acme.ukk.absensi.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.acme.ukk.absensi.entity.enums.StudentStatusEnum;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import static org.acme.ukk.absensi.core.util.ManipulateUtil.changeItOrNot;

@Entity
@Table(name = "siswa")
public class StudentsEntity extends PanacheEntityBase {
    
    @Id
    @Column(name = "nisn")
    @NotNull
    public Long nisn;

    @Column(name = "nama")
    @NotNull
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kelas")
    // @JsonBackReference
    @JsonIgnoreProperties("students")
    @NotNull
    public ClassEntity classGrade;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "gambar")
    @NotNull
    public ImageEntity image;

    @CreationTimestamp
    @Column(name = "tanggal_lahir")
    @NotNull
    public LocalDate dateOfBirth;

    @Enumerated
    @Column(name = "status")
    @NotNull
    public StudentStatusEnum status;


    public static Optional<StudentsEntity> findStudentsById(Long id) {
        return find("id =? 1", id).firstResultOptional();
    }

    public static List<StudentsEntity> findStudentsActive() {
        return find("status = ACTIVE").list();
    }

    public static List<StudentsEntity> findAllStudents() {
        return StudentsEntity.listAll();
    }

    public StudentsEntity updateStudent(StudentsEntity student) {
        student.nisn = changeItOrNot(nisn, student.nisn);
        student.name = changeItOrNot(name, student.name);
        student.classGrade = changeItOrNot(classGrade, student.classGrade);
        student.image = changeItOrNot(image, student.image);
        student.status = changeItOrNot(status, student.status);
        return student;
    }
}
