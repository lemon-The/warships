package com.lemonthe.java;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class DataBase {
    public static Connection getConnection() 
            throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(
                Paths.get("jdbc.properties"))) {
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);

        String url = props.getProperty("jdbc.url");
        String name = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, name, password);
    }
}
