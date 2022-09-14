package com.lemonthe.java;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.sql.*;
import java.io.*;

@Command(name = "delete",
        description = "Delete battle or battle memeber",
        mixinStandardHelpOptions = true,
        subcommands = {
            DeleteBattle.class,
            DeleteMember.class
        })
public class DeleteCommand {
}

@Command(name = "battle", mixinStandardHelpOptions = true,
        description = "Delete battle")
class DeleteBattle implements Runnable {
    @Parameters(index = "0")
    private String battleName;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query;
            query = "DELETE FROM battle_members "
                + "WHERE battle_name = '" + battleName + "';"
                + "DELETE FROM warships "
                + "WHERE name not in "
                    + "(SELECT w.name FROM warships w "
                    + "INNER JOIN battle_members bm "
                    + "ON w.name = bm.warship_name);"
                + "DELETE FROM battles "
                + "WHERE battle_name = '" + battleName + "'";
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

@Command(name = "member", mixinStandardHelpOptions = true,
        description = "Delete battle member")
class DeleteMember implements Runnable {
    @Parameters(index = "0")
    private String memberName;

    public void run() {
        try (Connection conn = DataBase.getConnection();
                Statement st = conn.createStatement()) {
            String query;
            query = "DELETE FROM battle_members "
                + "WHERE warship_name = '" + memberName +"';"
                + "DELETE FROM warships "
                + "WHERE name = '" + memberName + "'";
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
