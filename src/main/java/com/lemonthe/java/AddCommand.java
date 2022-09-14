package com.lemonthe.java;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.sql.*;
import java.io.*;
import java.time.*;

@Command(name = "add",
        description = "Add new battle or battle memeber",
        subcommands = {
            AddBattle.class,
            AddMember.class
        })
public class AddCommand {}

@Command(name = "battle", description = "Add battle")
class AddBattle implements Runnable {
    @Option(names = {"-n", "--name"}, required = true)
    private String battleName;
    @Option(names = {"-d", "--date"}, required = true)
    private String battleDate;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query = "INSERT INTO battles VALUES("
                + "'" + battleName + "', "
                + "'" + battleDate + "');";
            int res = st.executeUpdate(query);
            if (res > 0)
                System.out.println("INSERTED");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
}

@Command(name = "member", description = "Add battle member")
class AddMember implements Runnable {
    @Option(names = {"-n", "--name"}, required = true)
    private String shipName;
    @Option(names = {"-c", "--country"}, required = true)
    private Countries shipCountry;
    @Option(names = {"-h", "--class"}, required = true)
    private ShipClass shipClass;
    @Option(names = {"-o", "--commision"}, required = true)
    private LocalDate commisionDate;
    @Option(names = {"-e", "--decommision"}, required = true)
    private LocalDate decommisionDate;
    @Option(names = {"-b", "--battle"}, required = true)
    private String battleName;
    @Option(names = {"-r", "--result"}, required = true)
    private BattleResult battleResult;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {

            if (battleDate(st) == null) {
                System.out.println("INCORRECT battle name");
                return;
            }

            if (addShip(st) > 0)
                System.out.println("INSERTED");

            if (addMember(st) > 0)
                System.out.println("INSERTED");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int addShip(Statement st) throws SQLException {
        String query = "INSERT INTO warships VALUES("
            + "'" + shipName + "', "
            + "'" + shipCountry + "', "
            + "'" + shipClass + "', "
            + "'" + commisionDate + "', "
            + "'" + decommisionDate + "');";
        return st.executeUpdate(query);
    }
    private int addMember(Statement st) throws SQLException {
        String query = "INSERT INTO battle_members VALUES("
                + "nextval('battle_members_sequence'), '" 
                + battleName + "', '" 
                + shipName + "', '" 
                + battleResult + "');";
        return st.executeUpdate(query);
    }
    private LocalDate battleDate(Statement st) throws SQLException {
        String query = "SELECT battle_date FROM battles "
                + "WHERE battle_name = '"
                + battleName + "';";
        ResultSet rs = st.executeQuery(query);
        LocalDate battleDate;
        if (rs.next())
            battleDate = LocalDate.parse(rs.getString(1));
        else
            battleDate = null;
        return battleDate;
    }
}
