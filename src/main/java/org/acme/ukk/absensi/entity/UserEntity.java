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
@Table(name = "user")
public class UserEntity extends PanacheEntityBase {
    
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

    public static Optional<UserEntity> findUserByEmailAndPass(String email, String pass) {
        return find("email =? 1 AND password =? 2", email, pass).firstResultOptional();
    }
}
