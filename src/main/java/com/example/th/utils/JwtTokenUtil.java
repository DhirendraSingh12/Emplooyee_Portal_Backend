package com.example.th.utils;


import org.springframework.stereotype.Service;

import com.example.th.model.Employee;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenUtil {
	
	private String secret = "hireflex";
    private long expirationTime = 3 * 60 * 60 * 1000; // 3 hours in milliseconds
    
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getEmployeeIdFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject); // Assuming employeeId is stored as subject
    }

    public String getEmployeeNameFromToken(String jwtToken) {
        return extractClaim(jwtToken, claims -> (String) claims.get("employeeName"));
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        
        // Assuming "roles" is a claim that holds a List of LinkedHashMap<String, String>
        @SuppressWarnings("unchecked")
        List<Map<String, String>> roles = claims.get("roles", List.class);
        
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                .collect(Collectors.toList());
    }

    public String generateTokenWithDetails(Employee employee) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("employeeId", employee.getEmployeeId());
        claims.put("employeeName", employee.getEmployeeName());
        claims.put("designation", employee.getDesignation());
        claims.put("education", employee.getEducation());
        claims.put("address", employee.getAddress());
        claims.put("department", employee.getDepartment());
        return createToken(claims, employee.getEmployeeId());
    }
}