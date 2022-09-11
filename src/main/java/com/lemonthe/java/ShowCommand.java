package com.lemonthe.java;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.sql.*;
import java.io.*;

@Command(name = "show",
        description = "show",
        subcommands = {
            ShowShips.class,
            ShowBattles.class
        })
public class ShowCommand {
}

@Command(name = "ship", description = "show ship")
class ShowShips implements Runnable {
    @Option(names = {"-l"})
    public boolean showAll = false;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query;
            if (showAll)
                query = "SELECT * FROM warships;";
            else
                query = "SELECT name FROM warships;";
            ResultSet res = st.executeQuery(query);
            int columns = res.getMetaData().getColumnCount();
            int rawCount = 0;
            while (res.next()) {
                System.out.printf("%d) ", ++rawCount);
                for (int i = 0; i < columns; i++)
                    System.out.printf("%s | ", res.getString(i+1));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

@Command(name = "battle", description = "show battle")
class ShowBattles implements Runnable {
    @Option(names = {"-l"})
    public boolean showAll = false;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query;
            if (showAll)
                query = "SELECT * FROM battles;";
            else
                query = "SELECT battle_name FROM battles;";
            ResultSet res = st.executeQuery(query);
            int columns = res.getMetaData().getColumnCount();
            int rawCount = 0;
            while (res.next()) {
                System.out.printf("%d) ", ++rawCount);
                for (int i = 0; i < columns; i++)
                    System.out.printf("%s | ", res.getString(i+1));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


