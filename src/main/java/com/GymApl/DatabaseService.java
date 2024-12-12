package com.GymApl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String testDatabaseConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null && result == 1) {
                return "Połączenie z bazą danych działa poprawnie!";
            }
        } catch (Exception e) {
            return "Błąd połączenia z bazą danych: " + e.getMessage();
        }
        return "Błąd połączenia z bazą danych.";
    }



}
