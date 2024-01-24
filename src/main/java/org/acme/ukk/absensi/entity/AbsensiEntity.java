package org.acme.ukk.absensi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.acme.ukk.absensi.entity.enums.AbsenEnum;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "absensi")
public class AbsensiEntity extends PanacheEntityBase{
    
    @Id
    @GeneratedValue(generator = "bayu_id_gen")
    @Column(name = "id")
    @NotNull
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siswa")
    @JsonBackReference
    @NotNull
    public StudentsEntity student;

    @CreationTimestamp
    @Column(name = "tanggal")
    @NotNull
    public LocalDate date;

    @CreationTimestamp
    @Column(name = "waktu")
    @NotNull
    public LocalTime time;

    @Enumerated
    @Column(name = "status")
    @NotNull
    public AbsenEnum status;

    public static Optional<AbsensiEntity> findAbsenById(Long id) {
        return find("id =? 1", id).firstResultOptional();
    }

    public static List<AbsensiEntity> findAllAbsen() {
        return AbsensiEntity.listAll();
    }
    
}
