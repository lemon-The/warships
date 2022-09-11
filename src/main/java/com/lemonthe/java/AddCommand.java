package com.lemonthe.java;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.concurrent.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.time.*;

@Command(name = "add",
        description = "Add ship or battle",
        subcommands = {
            AddShip.class,
            AddBattle.class,
            AddMember.class
        })
public class AddCommand {

}

@Command(name = "ship", description = "Add ship")
class AddShip implements Runnable {
    @Option(names = {"-n", "--name"}, required = true)
    private String shipName;
    @Option(names = {"-c", "--country"}, required = true)
    private String shipCountry;
    @Option(names = {"-h", "--class"}, required = true)
    private String shipClass;
    @Option(names = {"-o", "--commision"}, required = true)
    private LocalDate commisionDate;
    @Option(names = {"-e", "--decommision"}, required = true)
    private LocalDate decommisionDate;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {

            String query = "INSERT INTO warships VALUES("
                + "'" + shipName + "', "
                + "'" + shipCountry + "', "
                + "'" + shipClass + "', "
                + "'" + commisionDate + "', "
                + "'" + decommisionDate + "');";
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

@Command(name = "member", description = "add battle member")
class AddMember implements Runnable {
    @Option(names = {"-b", "--battle"}, required = true)
    private String battleName;
    @Option(names = {"-s", "--ship"}, required = true)
    private String shipName;
    @Option(names = {"-r", "--result"}, required = true)
    private BattleResult battleResult;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query = "SELECT battle_name, battle_date FROM battles;";
            ResultSet res = st.executeQuery(query);
            boolean doesContain = false;
            LocalDate comDate = null;
            LocalDate decomDate = null;
            LocalDate battleDate = null;
            while (res.next()) {
                if (battleName.equals(res.getString(1))) {
                    battleDate = LocalDate.parse(res.getString(2));
                    doesContain = true;
                    break;
                }
            }
            if (!doesContain) {
                System.out.println("INCORRECT battle name");
                return;
            }

            query = "SELECT name, commission_date, decommission_date FROM warships;";
            res = st.executeQuery(query);
            doesContain = false;
            while (res.next()) {
                if (shipName.equals(res.getString(1))) {
                    comDate = LocalDate.parse(res.getString(2));
                    decomDate = LocalDate.parse(res.getString(3));
                    doesContain = true;
                    break;
                }
            }
            if (!doesContain) {
                System.out.println("INCORRECT ship name");
                return;
            }

            if (!comDate.isBefore(battleDate)) {
                System.out.println("Commission date after battle date!");
                return;
            } else if (!decomDate.isAfter(battleDate)) {
                System.out.println("Decommission date before battle date!");
                return;
            }

            query = "INSERT INTO battle_members VALUES(nextval('battle_members_sequence'), '" + battleName + "', '" + shipName + "', '" + battleResult + "');";

            int ress = st.executeUpdate(query);
            if (ress > 0)
                System.out.println("INSERTED");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    enum BattleResult {
        SUNK,
        DAMAGED,
        SURVIVED
    }
}
