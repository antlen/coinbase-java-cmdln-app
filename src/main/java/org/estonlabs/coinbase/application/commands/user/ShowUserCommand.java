package org.estonlabs.coinbase.application.commands.user;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.ShowObjectCommand;
import org.estonlabs.coinbase.client.CbApiException;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.user.CbUser;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "show_user")
public class ShowUserCommand extends ShowObjectCommand<CbUser> {

    @CommandLine.Option(names = {"-auth"}, description = "Whether to display the Oath info.  If omitted the users basic info will be displayed.")
    boolean auth;

    @Override
    protected CbUser getData() {
        CbClient client = CbClientWrapper.INSTANCE.getClient();
        return id == null ? client.getUser() : client.getUser(id);
    }

    @Override
    protected String[] summarizeFields(CbUser u) {
        return new String[]{u.getId(), u.getName()};
    }
}
