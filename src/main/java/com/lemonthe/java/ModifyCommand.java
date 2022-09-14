package com.lemonthe.java;

import picocli.CommandLine.*;
import java.time.LocalDate;
import java.sql.*;
import java.io.*;

//TODO 
//There is a problem with several battles for one ship

@Command(name = "modify",
        description = "Modify battle or battle memeber",
        subcommands = {
            ModifyBattle.class
        })
public class ModifyCommand {}


@Command(name = "battle", description = "Modify battle")
class ModifyBattle implements Runnable {
    @Option(names = {"-n", "--name"})
    private String newBattleName = null;
    @Option(names = {"-d", "--date"})
    private LocalDate newBattleDate = null;
    @Parameters(index = "0")
    private String battleName;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            if (newBattleDate != null) {
                if (newBattleDate.isBefore(LocalDate.parse("1939-01-01"))
                    || newBattleDate.isAfter(LocalDate.parse("1945-01-02"))) {
                    System.out.println("New battle date was not in WW2");
                    return;
                }
                String conf = getConflictedShip(st);
                if (conf != null) {
                    System.out.println("New battle date conflicts with"
                            + conf + "warship");
                    return;
                }
                if (updateBattleDate(st) > 0)
                    System.out.println("UPDATED");
            }
            if (newBattleName != null)
                if (updateBattleName(st) > 0)
                    System.out.println("UPDATED");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getConflictedShip(Statement st) throws SQLException {
        String query = "SELECT w.name, w.commission_date, "
            + "w.decommission_date FROM warships w "
            + "INNER JOIN battle_members bm ON bm.warship_name = w.name "
            + "WHERE bm.battle_name = '" + battleName + "'";
        ResultSet res = st.executeQuery(query);
        while (res.next()) {
            LocalDate com = LocalDate.parse(res.getString(2));
            LocalDate decom = LocalDate.parse(res.getString(3));
            if (newBattleDate.isBefore(com) || newBattleDate.isAfter(decom))
                return res.getString(1);
        }
        return null;
    }
    private int updateBattleDate(Statement st) throws SQLException {
        String query = "UPDATE battles SET battle_date = '"
            + newBattleDate + "' WHERE battle_name = '"
            + battleName + "'";
            return st.executeUpdate(query);
    }
    private int updateBattleName(Statement st) throws SQLException {
        //LocalDate bDate;
        String query;
        //if (newBattleDate != null) {
        //    bDate = newBattleDate;
        //} else {
        //    query = "SELECT battle_date FROM battles "
        //        + "WHERE battle_name = '" + battleName + "'";
        //    ResultSet res = st.executeQuery(query);
        //    res.next();
        //    bDate = LocalDate.parse(res.getString(1));
        //}
        //System.out.println(bDate);
        
        query = "UPDATE battles SET battle_name = '"
            + newBattleName + "' WHERE battle_name = '"
            + battleName + "'";

        
        //query = "BEGIN;"
        //    + "INSERT INTO battles VALUES ('" + newBattleName + "', '"
        //    + bDate + "');"
        //    + "UPDATE battle_members "
        //    + "SET battle_name = '" + newBattleName
        //    + "' WHERE battle_name = '" + battleName + "';"
        //    + "DELETE FROM battles WHERE battle_name = '"
        //    + battleName + "';"
        //    + "COMMIT;";
        return st.executeUpdate(query);
    }
}

@Command(name = "member", description = "Modify battle memeber")
class ModifyMemeber implements Runnable {
    @Option(names = {"-n", "--name"}, required = true)
    private String newShipName = null;
    @Option(names = {"-c", "--country"}, required = true)
    private Countries newShipCountry = null;
    @Option(names = {"-h", "--class"}, required = true)
    private ShipClass newShipClass = null;
    @Option(names = {"-o", "--commission"}, required = true)
    private LocalDate newCommissionDate = null;
    @Option(names = {"-e", "--decommission"}, required = true)
    private LocalDate newDecommissionDate = null;
    @Option(names = {"-b", "--battle"}, required = true)
    private String newBattleName = null;
    @Option(names = {"-r", "--result"}, required = true)
    private BattleResult newBattleResult = null;
    @Parameters(index = "0")
    private String shipName = null;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            if (newShipName != null)
                if (updateShipName(st) > 0)
                    System.out.println("UPDATED");
            if (newShipCountry != null)
                if (updateShipCountry(st) > 0)
                    System.out.println("UPDATED");
            if (newShipClass != null)
                if (updateShipClass(st) > 0)
                    System.out.println("UPDATED");
            if (newCommissionDate != null) {
                String conf = getConflictedBattle(st);
                if (conf != null) {
                    System.out.println("New commission/decommission date "
                            + "conflicts with" + conf + "battle");
                    return;
                }
                if (updateCommissionDate(st) > 0)
                    System.out.println("UPDATED");
            }
            if (newDecommissionDate != null) {
                String conf = getConflictedBattle(st);
                if (conf != null) {
                    System.out.println("New commission/decommission date "
                            + "conflicts with" + conf + "battle");
                    return;
                }
                if (updateDecommissionDate(st) > 0)
                    System.out.println("UPDATED");
            }
            if (newBattleName != null)
                if (updateBattleName(st) > 0)
                    System.out.println("UPDATED");
            if (newBattleResult != null)
                if (updateBattleResult(st) > 0)
                    System.out.println("UPDATED");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int updateShipName(Statement st) throws SQLException {
        String query = "UPDATE warships SET name = '"
            + newShipName + "' WHERE name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }
    private int updateShipCountry(Statement st) throws SQLException {
        String query = "UPDATE warships SET country = '"
            + newShipCountry + "' WHERE name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }
    private int updateShipClass(Statement st) throws SQLException {
        String query = "UPDATE warships SET class = '"
            + newShipClass + "' WHERE name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }
    private int updateCommissionDate(Statement st) throws SQLException {
        String query = "UPDATE warships SET commission_date = '"
            + newCommissionDate + "' WHERE name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }
    private int updateDecommissionDate(Statement st) throws SQLException {
        String query = "UPDATE warships SET decommission_date = '"
            + newDecommissionDate + "' WHERE name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }
    private int updateBattleName(Statement st) throws SQLException {
        String query = "UPDATE battle_members SET battle_name = '"
            + newBattleName + "' WHERE warship_name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }
    private int updateBattleResult(Statement st) throws SQLException {
        String query = "UPDATE battle_members SET result = '"
            + newBattleResult + "' WHERE warship_name = '"
            + shipName + "'";
        return st.executeUpdate(query);
    }

    private String getConflictedBattle(Statement st) throws SQLException {
        String query = "SELECT b.battle_name, b.battle_date "
            + "FROM battles b INNER JOIN battle_members bm "
            + "ON bm.battle_name = b.battle_name "
            + "WHERE bm.warship_name = '" + shipName + "'";
        ResultSet res = st.executeQuery(query);
        while (res.next()) {
            LocalDate date = LocalDate.parse(res.getString(2));
            if (newCommissionDate != null && newCommissionDate.isAfter(date))
                return res.getString(1);
            if (newDecommissionDate != null && newDecommissionDate.isBefore(date))
                return res.getString(1);
        }
        return null;
    }
}
