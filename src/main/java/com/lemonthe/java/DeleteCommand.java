package com.lemonthe.java;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.sql.*;
import java.io.*;

@Command(name = "delete",
        description = "delete command",
        subcommands = {
            DeleteShip.class,
            DeleteBattle.class
        })
public class DeleteCommand {
}

@Command(name = "ship", description = "delete ship")
class DeleteShip implements Runnable {
    @Parameters(index = "0")
    private String shipName;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query = "DELETE FROM warships WHERE "
                + "name = '" + shipName + "';"; 
            int res = st.executeUpdate(query);
            if (res > 0)
                System.out.println("DELETED");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

@Command(name = "battle", description = "delete battle")
class DeleteBattle implements Runnable {
    @Parameters(index = "0")
    private String battleName;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query = "DELETE FROM battles WHERE "
                + "battle_name = '" + battleName + "';"; 
            int res = st.executeUpdate(query);
            if (res > 0)
                System.out.println("DELETED");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
