package org.acme.ukk.absensi.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.acme.ukk.absensi.entity.enums.AbsenEnum;
import org.hibernate.annotations.CreationTimestamp;

import static org.acme.ukk.absensi.core.util.ManipulateUtil.changeItOrNot;

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
import lombok.Getter;

@Entity
@Table(name = "absensi")
@Getter
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

    public static List<AbsensiEntity> findAllAbsensiByDate(LocalDate date) {
        return find("date = ? 1", date).list();
    }

    public static List<AbsensiEntity> findAbsensiTodayByStatus(AbsenEnum status, LocalDate date) {
        return find("status = ? 1 and date = ? 2", status, date).list();
    }

    public static List<AbsensiEntity> findAbsensiByDate(LocalDate date) {
        return find("date = ? 1", date).list();
    }

    public static List<AbsensiEntity> findAllAbsen() {
        return AbsensiEntity.listAll();
    }

    public AbsensiEntity updateAbsensi(AbsensiEntity absensi) {
        absensi.status = changeItOrNot(status, absensi.status);
        absensi.time   = changeItOrNot(time, absensi.time);
        return absensi;
    }

    
}
