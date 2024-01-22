package org.acme.ukk.absensi.model.body;

import org.acme.ukk.absensi.entity.AdminDasboardEntity;

public record AdminDashboardBody(
    String name,
    String email,
    String password
) {

    public AdminDasboardEntity mapToDasboardEntity() {
        var admin = new AdminDasboardEntity();
        admin.name = name;
        admin.email = email;
        admin.password = password;
        return admin;
    }

}
