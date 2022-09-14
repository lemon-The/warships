package com.lemonthe.java;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "warships",
        description = "Tool for sea battles and warships management",
        mixinStandardHelpOptions = true,
        subcommands = {
            AddCommand.class,
            DeleteCommand.class,
            ShowCommand.class,
            ModifyCommand.class
        })
public class App {
    public static void main(String[] args) {
        new CommandLine(App.class).execute(args);
    }
}
