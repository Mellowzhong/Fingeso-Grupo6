package com.example.BackendHabitatDigital.entities;

public enum RoleEntity {
    ADMIN,
    EXTERNAL,
    COMUN,
    CORREDOR,
    OWNER;

    public static RoleEntity fromString(String roleStr) throws Exception {
        for (RoleEntity role : RoleEntity.values()) {
            if (role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new Exception("Role " + roleStr + " not found.");
    }
}
