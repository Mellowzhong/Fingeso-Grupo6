package com.example.BackendHabitatDigital.entities;

/*
    Descripcion: Esta enumeración `RoleEntity` define los diferentes roles que un usuario
    puede tener en el sistema. Los roles incluyen `ADMIN`, `EXTERNAL`, `COMUN`, `CORREDOR`,
    y `OWNER`. Cada rol representa un nivel de acceso o permisos específicos dentro del sistema.
 */
public enum RoleEntity {
    ADMIN,
    EXTERNAL,
    COMUN,
    CORREDOR,
    OWNER;

    /*
        Descripcion: Este método estático convierte una cadena de texto en un valor de `RoleEntity`.
        Recorre todos los valores de la enumeración para encontrar un rol que coincida con la cadena
        proporcionada, sin importar si las mayúsculas y minúsculas coinciden. Si no se encuentra
        una coincidencia, lanza una excepción.
     */
    public static RoleEntity fromString(String roleStr) throws Exception {
        for (RoleEntity role : RoleEntity.values()) {
            if (role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new Exception("Role " + roleStr + " not found.");
    }
}
