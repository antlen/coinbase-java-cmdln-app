package com.coinbase.application.commands;

import picocli.CommandLine;

@CommandLine.Command(name="exit")
public class ExitCommand implements Runnable{
    @Override
    public void run() {
        System.exit(0);
    }
}
