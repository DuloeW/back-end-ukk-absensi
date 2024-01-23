package org.acme.ukk.absensi.model.body;

import org.acme.ukk.absensi.entity.UserEntity;

public record UserBody(
    String email,
    String password
) {
    
    public UserEntity mapToUserEntity() {
        var user = new UserEntity();
        user.email = email;
        user.password = password;
        return user;
    }

}
