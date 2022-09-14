package com.lemonthe.java;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.sql.*;
import java.io.*;

@Command(name = "show",
        description = "Show battle(s) or battle memeber(s)",
        subcommands = {
            ShowBattles.class,
            ShowMember.class
        })
public class ShowCommand {
}

@Command(name = "battle", description = "Show battle. To show all battles use 'list'")
class ShowBattles implements Runnable {
    @Parameters(index = "0")
    public String battleName = "list";

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query;
            if (battleName.equals("list"))
                query = "SELECT * FROM battles;";
            else
                query =  "SELECT w.name, w.country, w.class, bm.result, "
                    + "w.commission_date, w.decommission_date "
                    + "FROM warships w INNER JOIN battle_members bm "
                    + "ON bm.warship_name = w.name "
                    + "WHERE bm.battle_name = '" + battleName + "';";
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

@Command(name = "member", description = "Show battle member. To show all members use 'list'")
class ShowMember implements Runnable {
    @Parameters(index = "0")
    public String shipName = "list";

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query;
            if (shipName.equals("list"))
                query = "SELECT name FROM warships";
            else
                query = "SELECT country, class, commission_date, "
                    + "decommission_date FROM warships "
                    + "WHERE name = '" + shipName + "'";
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
