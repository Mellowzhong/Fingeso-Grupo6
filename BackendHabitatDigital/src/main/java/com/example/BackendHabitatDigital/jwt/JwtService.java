package com.example.BackendHabitatDigital.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
    Descripcion: Esta clase `JwtService` es un servicio que se encarga de manejar la generación, validación y manipulación
    de tokens JWT (JSON Web Tokens) en el sistema. Proporciona métodos para crear tokens, extraer información de ellos,
    y validar su autenticidad y vigencia.
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "09823409823049823409884578439875021982310943F";

    /*
        Descripcion: Este método genera un token JWT para un usuario específico. Llama a un método privado
        que permite agregar reclamaciones adicionales si es necesario.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /*
        Descripcion: Este método privado genera un token JWT, permitiendo agregar reclamaciones adicionales
        (extraClaims). El token incluye el nombre de usuario, la fecha de emisión, y la fecha de expiración.
     */
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                //Token information
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1440)) // 1 dia
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
        Descripcion: Este método privado devuelve la clave secreta convertida en un objeto `Key`
        que se utiliza para firmar y verificar los tokens JWT.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
        Descripcion: Este método extrae el nombre de usuario del token JWT.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /*
        Descripcion: Este método verifica si el token JWT es válido comparando el nombre de usuario
        del token con el nombre de usuario de `UserDetails` y verificando si el token no ha expirado.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /*
        Descripcion: Este método privado extrae todas las reclamaciones (claims) de un token JWT.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
        Descripcion: Este método genérico extrae una reclamación específica de un token JWT utilizando
        una función de resolución de reclamaciones.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
        Descripcion: Este método privado extrae la fecha de expiración de un token JWT.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /*
        Descripcion: Este método verifica si un token JWT ha expirado.
     */
    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
