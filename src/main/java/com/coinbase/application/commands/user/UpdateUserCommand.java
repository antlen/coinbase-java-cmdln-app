package com.coinbase.application.commands.user;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.domain.user.CbUser;
import com.coinbase.domain.user.request.CbUserUpdateBuilder;
import picocli.CommandLine;

import java.util.TimeZone;

@CommandLine.Command(name = "update", description = "update the details of the current user.",
        mixinStandardHelpOptions = true)
public class UpdateUserCommand extends ShowUserCommand{

    @CommandLine.Option(names = {"-tz"}, description = "The user's timezone")
    protected String timezone;

    @CommandLine.Option(names = {"-name"}, description = "The user's name")
    protected String name;

    @CommandLine.Option(names = {"-currency"}, description = "The user's currency")
    protected String currency;

    @Override
    protected CbUser modify(CbUser u) {
        CoinbaseClient client = CbClientWrapper.INSTANCE.getClient();

        CbUserUpdateBuilder builder = new CbUserUpdateBuilder(u);
        boolean hasParam = false;
        if(timezone != null){
            TimeZone tz = TimeZone.getTimeZone(timezone);
            builder.setTimeZone(tz.getID());
            hasParam=true;
        }

        if(name != null){
            builder.setName(name);
            hasParam=true;
        }

        if(currency != null){
            builder.setNativeCurrency(currency);
            hasParam=true;
        }

        if(!hasParam){
            throw new NullPointerException("Need to set a param to update.  Run 'account update --help' for info.");
        }

        return client.updateUser(builder.build());
    }
}
