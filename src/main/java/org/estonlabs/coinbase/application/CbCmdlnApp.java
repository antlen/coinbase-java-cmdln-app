package org.estonlabs.coinbase.application;

import org.estonlabs.coinbase.application.commands.HelpCommand;
import org.estonlabs.coinbase.application.commands.LogJSONCommand;
import org.estonlabs.coinbase.application.commands.LoginCommand;
import org.estonlabs.coinbase.application.commands.TimeZoneCommand;
import org.estonlabs.coinbase.application.commands.accounts.AccountCommands;
import org.estonlabs.coinbase.application.commands.accounts.ShowAccountCommand;
import org.estonlabs.coinbase.application.commands.accounts.ShowAllAccountsCommand;
import org.estonlabs.coinbase.application.commands.accounts.UpdateAccountCommand;
import org.estonlabs.coinbase.application.commands.price.PriceCommands;
import org.estonlabs.coinbase.application.commands.price.ShowCurrencyCodesCommand;
import org.estonlabs.coinbase.application.commands.price.ShowPriceCommand;
import org.estonlabs.coinbase.application.commands.transaction.PaymentMethodCommands;
import org.estonlabs.coinbase.application.commands.transaction.ShowPaymentMethodCommand;
import org.estonlabs.coinbase.application.commands.transaction.ShowPaymentMethodsCommand;
import org.estonlabs.coinbase.application.commands.user.ShowUserCommand;
import org.estonlabs.coinbase.application.commands.user.UpdateUserCommand;
import org.estonlabs.coinbase.application.commands.user.UserCommands;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.domain.system.CbTime;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CbCmdlnApp implements Runnable{

    public static void put(Map<String, CommandLine> m, Object o){
        CommandLine cl = new CommandLine(o);
        m.put(cl.getCommandName(), cl);
    }

    @Override
    public void run(){

        final Map<String, CommandLine> commands = new HashMap<>();
        put(commands, new LogJSONCommand());
        put(commands, new AccountCommands());
        put(commands, new LoginCommand());
        put(commands, new UserCommands());
        put(commands, new PaymentMethodCommands());
        put(commands, new HelpCommand(commands));
        put(commands, new TimeZoneCommand());
        put(commands, new PriceCommands());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commands.get("help").execute();

        if(attemptEagerLoginFromSystemProperty()){
            System.out.println("******Logged into Coinbase API.***********");
            logTime(CbClientWrapper.INSTANCE.getClient());
        }else{
            System.out.println("********************** PLEASE READ ****************************");
            System.out.println("Run 'login -s <secret> -a <API key>' to login.  Alternatively pass -DCbSecret -DCbAPIKey in the program args to auto login");
            System.out.println("***************************************************************");
        }
        CbClient client = CbClientWrapper.INSTANCE.getClient();
        if(client.isLConnectedToProd()){
            System.out.println("!!!!!!!!!!! WARNING --- CONNECTED TO LIVE PROD ACCOUNT !!!!!!!!!!! ");
        }else{
            System.out.println("*** Connected to the Sandbox ***");
        }

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
            String passphrase = System.getProperty("CbPassphrase");
            if(passphrase!=null)passphrase.trim();
            String s = System.getProperty("useSandbox");
            boolean useSandbox = (s==null)?false:Boolean.parseBoolean(s.trim());
            if (api != null && secret != null) {

                CbClientWrapper.INSTANCE.init(api.trim(),  secret.getBytes(), useSandbox, passphrase);
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

    private void logTime(CbClient client ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String before = LocalDateTime.now().format(formatter);
        CbTime time = client.getServerTime();
        DateTimeFormatter fromIso = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime serverTime = LocalDateTime.parse(
                time.getIso().replaceAll("T", " ").replaceAll("Z",""),fromIso);
        // LocalDateTime serverTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time.getEpoch()), ZoneId.systemDefault());
        System.out.println("Local Time: " + before);
        System.out.println("Server Time: " + serverTime.format(formatter));
        System.out.println("Local Time: " + LocalDateTime.now().format(formatter));
    }


    public static void main(String args[]) throws Exception {
        new CbCmdlnApp().run();
    }
}
