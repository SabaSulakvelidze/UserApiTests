package com.credobank.Utils;

import java.sql.*;
import java.time.LocalDateTime;

public class SqlLiteTestResultGenerator {
    private static final String DB_URL = "jdbc:sqlite:test_results.db";

    static {
        createTableIfNotExists();
    }


    private static void createTableIfNotExists() {
        //language=SQL
        String sql = """
                    CREATE TABLE IF NOT EXISTS test_results (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        test_name TEXT UNIQUE,
                        status TEXT,
                        execution_time DATETIME)
                """;
        try (Connection connection = DriverManager.getConnection(DB_URL); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize SQLite table", e);
        }
    }


    public static void saveOrUpdate(String testName, String status) {
        //language=SQL
        String selectQuery = """
                    SELECT id FROM test_results WHERE test_name = ?
                """;
        //language=SQL
        String updateQuery = """
                    UPDATE test_results SET status = ?, execution_time = ? WHERE test_name = ?
                """;
        //language=SQL
        String insertQuery = """
                    INSERT INTO test_results (test_name, status, execution_time) VALUES (?, ?, ?)
                """;

        String now = LocalDateTime.now().toString();

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement selectPS = connection.prepareStatement(selectQuery)) {
                selectPS.setString(1, testName);
                ResultSet resultSet = selectPS.executeQuery();

                if (resultSet.next()) {
                    try (PreparedStatement updatePS = connection.prepareStatement(updateQuery)) {
                        updatePS.setString(1, status);
                        updatePS.setString(2, now);
                        updatePS.setString(3, testName);
                        updatePS.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertPS = connection.prepareStatement(insertQuery)) {
                        insertPS.setString(1, testName);
                        insertPS.setString(2, status);
                        insertPS.setString(3, now);
                        insertPS.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save test result for: " + testName, e);
        }
    }


}
