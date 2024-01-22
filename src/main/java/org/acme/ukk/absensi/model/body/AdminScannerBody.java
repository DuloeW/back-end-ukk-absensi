package org.acme.ukk.absensi.model.body;

import org.acme.ukk.absensi.entity.AdminScannerEntity;

public record AdminScannerBody(
    String email,
    String password
) {

    public AdminScannerEntity mapToAdminScannerEntity() {
        var admin = new AdminScannerEntity();
        admin.email = email;
        admin.password = password;
        return admin;
    }
    
}
