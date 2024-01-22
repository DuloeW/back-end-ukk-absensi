package org.acme.ukk.absensi.entity;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_scanner")
public class AdminScannerEntity extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(generator = "bayu_id_gen")
    @Column(name = "id")
    @NotNull
    public Long id;

    @Column(name = "email")
    @NotNull
    public String email;

    @Column(name = "password")
    @NotNull
    public String password;

    public static Optional<AdminScannerEntity> findAdminScannerById(Long id) {
        return find("id =? 1", id).firstResultOptional();
    } 

    public static Optional<AdminScannerEntity> findAdminByEmailAndPass(String email, String password) {
        return find("email =? 1 AND password password", email, password).firstResultOptional();
    }
}
