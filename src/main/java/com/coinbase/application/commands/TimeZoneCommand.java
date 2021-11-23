package com.coinbase.application.commands;

import picocli.CommandLine;

import java.util.Map;
import java.util.TimeZone;

@CommandLine.Command(name="timezone")
public class TimeZoneCommand implements Runnable{

    @CommandLine.Option(names = {"-filter"}, description = "The timezone should include this word")
    String inc;

    @Override
    public void run() {
        for(String tz : TimeZone.getAvailableIDs()){
            if(inc == null || tz.contains(inc)){
                System.out.println(tz);
            }
        }
    }
}
