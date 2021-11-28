package com.coinbase.application;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.application.commands.HelpCommand;
import com.coinbase.application.commands.LogJSONCommand;
import com.coinbase.application.commands.LoginCommand;
import com.coinbase.application.commands.address.AddressCommands;
import com.coinbase.application.commands.exchange.ExchangeCommands;
import com.coinbase.application.commands.TimeZoneCommand;
import com.coinbase.application.commands.accounts.AccountCommands;
import com.coinbase.application.commands.deposit.DepositCommands;
import com.coinbase.application.commands.deposit.WithdrawalCommands;
import com.coinbase.application.commands.price.PriceCommands;
import com.coinbase.application.commands.transaction.PaymentMethodCommands;
import com.coinbase.application.commands.user.UserCommands;
import com.coinbase.client.CoinbaseSyncClient;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * A simple command line app for running commands against the coinbase API.
 */
public class CoinbaseCommandLineApp implements Runnable{

    public static void put(Map<String, CommandLine> m, Object o){
        CommandLine cl = new CommandLine(o);
        m.put(cl.getCommandName(), cl);
    }

    @Override
    public void run(){

        final Map<String, CommandLine> commands = new HashMap<>();
        put(commands, new LogJSONCommand());
        put(commands, new AccountCommands());
        put(commands, new AddressCommands());
        put(commands, new ExchangeCommands());
        put(commands, new LoginCommand());
        put(commands, new UserCommands());
        put(commands, new PaymentMethodCommands());
        put(commands, new DepositCommands());
        put(commands, new WithdrawalCommands());
        put(commands, new HelpCommand(commands));
        put(commands, new TimeZoneCommand());
        put(commands, new PriceCommands());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commands.get("help").execute();

        if(!attemptEagerLoginFromSystemProperty()){
            System.out.println("********************** PLEASE READ ****************************");
            System.out.println("Run 'login -s <secret> -a <API key>' to login.  Alternatively pass -DCbSecret -DCbAPIKey in the program args to auto login");
            System.out.println("***************************************************************");
        }
        CoinbaseSyncClient client = CbClientWrapper.INSTANCE.getClient();
        System.out.println("!!!!!!!!!!! WARNING --- CONNECTED TO LIVE PROD ACCOUNT !!!!!!!!!!! ");

        while(true) {
            System.out.print(">>");
            // Reading data using readLine
            String command = null;
            try {
                command = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] params = command.split(" ");
            CommandLine cl = params[0]==null?null: commands.get(params[0]);
            if(cl == null){
                System.out.println("Invalid command.Run 'help' for the command list.");
            }else{
                cl.execute(Arrays.copyOfRange(params, 1, params.length));
            }
        }
    }

    public boolean attemptEagerLoginFromSystemProperty(){
        try {
            String secret = System.getProperty("CbSecret");
            String api = System.getProperty("CbAPIKey");

            if (api != null && secret != null) {

                new LoginCommand.Init(secret,api).run();

                String tmp = System.getProperty("logJSON");
                boolean logJSON = (tmp==null)?false:Boolean.parseBoolean(tmp.trim());
                if(logJSON){
                    CbClientWrapper.INSTANCE.getClient().setLogResponsesEnabled(logJSON);
                }
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        new CoinbaseCommandLineApp().run();
    }
}
