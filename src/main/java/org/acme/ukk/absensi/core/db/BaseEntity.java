package org.acme.ukk.absensi.core.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

public abstract class BaseEntity extends PanacheEntityBase {
    protected abstract void setDefaultValBeforeInsert();

    public void insert() {
        setDefaultValBeforeInsert();
        persist();
    }
}
