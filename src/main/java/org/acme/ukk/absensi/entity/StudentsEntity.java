package org.acme.ukk.absensi.entity;

import static org.acme.ukk.absensi.core.util.ManipulateUtil.changeItOrNot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.acme.ukk.absensi.entity.enums.GradeEnum;
import org.acme.ukk.absensi.entity.enums.MajorEnum;
import org.acme.ukk.absensi.entity.enums.StudentStatusEnum;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "siswa")
public class StudentsEntity extends PanacheEntityBase {

  @Id
  @GeneratedValue(generator = "bayu_id_gen")
  @NotNull
  @Column(name = "id")
  public Long id;

  @Column(name = "nisn")
  @NotNull
  public String nisn;

  @Column(name = "nama")
  @NotNull
  public String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "kelas")
  @JsonIgnoreProperties("students")
  @NotNull
  public ClassEntity classGrade;

  @Column(name = "tanggal_lahir")
  @NotNull
  public LocalDate dateOfBirth;

  @Enumerated
  @Column(name = "status")
  @NotNull
  public StudentStatusEnum status;

  @OneToOne(fetch = FetchType.LAZY)
  @JsonManagedReference
  @JoinColumn(name = "gambar")
  @NotNull
  public ImageEntity image;

  @OneToMany(
    mappedBy = "student",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER
  )
  @JsonManagedReference
  public List<AbsensiEntity> absensi;

  public static Optional<StudentsEntity> findStudentsById(Long id) {
    return find("id =? 1", id).firstResultOptional();
  }

  public static List<StudentsEntity> findStudentsActive() {
    return find("status = ACTIVE").list();
  }

  public static Optional<StudentsEntity> findStudentByNisn(String nisn) {
    return find("nisn = ?1 and status = ACTIVE", nisn).firstResultOptional();
  }

  public static List<StudentsEntity> findStudentByName(String name) {
    return find("name like ?1", "%" + name + "%").list();
  }

  public static List<StudentsEntity> findAllStudents() {
    return StudentsEntity.listAll();
  }

  public static List<StudentsEntity> findAllStudentByClass(GradeEnum grade, MajorEnum major) {
    return find("classGrade.grade = ?1 and classGrade.major = ?2 and status = ACTIVE", grade, major).list();
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
