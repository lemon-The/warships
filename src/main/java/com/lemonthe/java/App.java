package com.lemonthe.java;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.*;

@Command(name = "test", description = "test description",
        subcommands = {
            AddCommand.class,
            DeleteCommand.class,
            ShowCommand.class
        })
public class App {
    @Option(names = {"-o", "--optrion"}, description = "option descript")
    private int option = 123;


    public static void main(String[] args) {
        System.out.println(new CommandLine(new App()).execute(args));
    }
}
