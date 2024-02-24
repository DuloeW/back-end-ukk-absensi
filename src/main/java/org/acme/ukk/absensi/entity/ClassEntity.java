package org.acme.ukk.absensi.entity;

import java.util.List;
import java.util.Optional;

import org.acme.ukk.absensi.entity.enums.GradeEnum;
import org.acme.ukk.absensi.entity.enums.MajorEnum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "kelas")
// @JsonIgnoreProperties("students")
public class ClassEntity extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(generator = "bayu_id_gen")
    @Column(name = "id")
    @NotNull
    public Long id;

    @Enumerated
    @Column(name = "tingkat", columnDefinition = "tinyint")
    @NotNull
    public GradeEnum grade;

    @Enumerated
    @Column(name = "jurusan", columnDefinition = "tinyint")
    @NotNull
    public MajorEnum major;

    @OneToMany(mappedBy = "classGrade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonIgnoreProperties(value = "classGrade")
    // @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<StudentsEntity> students;

    public static Optional<ClassEntity> findClassById(Long id) {
        return find("id =? 1", id).firstResultOptional();
    }

    public static Optional<ClassEntity> findClassByMajorAndGrade(MajorEnum major, GradeEnum grade) {
        return find("major = ? 1 and grade = ? 2", major, grade).firstResultOptional();
    }


    public static List<ClassEntity> findAllClass() {
        return ClassEntity.listAll();
    }


}
