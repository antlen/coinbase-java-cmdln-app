package org.estonlabs.coinbase.application.commands.user;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.domain.user.CbUser;
import org.estonlabs.coinbase.domain.user.CbUserUpdateRequest;
import org.estonlabs.coinbase.domain.user.CbUserUpdateBuilder;
import picocli.CommandLine;

import java.util.TimeZone;

@CommandLine.Command(name = "update_user")
public class UpdateUserCommand extends ShowUserCommand{

    @CommandLine.Option(names = {"-tz"}, description = "The users timezone")
    protected String timezone;

    @Override
    protected CbUser modify(CbUser u) {
        CbClient client = CbClientWrapper.INSTANCE.getClient();
        TimeZone tz = TimeZone.getTimeZone(timezone);
        CbUserUpdateRequest update = new CbUserUpdateBuilder(u).setTimeZone(tz.getID()).build();

        return client.updateUser(update);
    }
}
