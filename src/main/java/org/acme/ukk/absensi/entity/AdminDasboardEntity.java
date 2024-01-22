package org.acme.ukk.absensi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;

@Entity
@Table(name = "admin_dasboard")
public class AdminDasboardEntity extends PanacheEntityBase {

  @Id
  @GeneratedValue(generator = "bayu_id_gen")
  @NotNull
  @Column(name = "id")
  public Long id;

  @Column(name = "nama")
  @NotNull
  public String name;

  @Column(name = "email")
  @NotNull
  public String email;

  @Column(name = "password")
  @NotNull
  public String password;

  public static Optional<AdminDasboardEntity> findAdminDasboardById(Long id) {
    return find("id ?= 1", id).firstResultOptional();
  }

  public static Optional<AdminDasboardEntity> findAdminDasboardByEmailAndPass(
    String email,
    String pass
  ) {
    return find("email = ? 1 AND password = ? 2 ", email, pass).firstResultOptional();
  }
}
