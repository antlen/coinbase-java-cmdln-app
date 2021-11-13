package org.estonlabs.coinbase.application.commands.accounts;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.application.commands.user.ShowUserCommand;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.client.CbNameUpdateRequest;
import org.estonlabs.coinbase.domain.account.CbAccount;
import org.estonlabs.coinbase.domain.user.CbUser;
import org.estonlabs.coinbase.domain.user.CbUserUpdateBuilder;
import org.estonlabs.coinbase.domain.user.CbUserUpdateRequest;
import picocli.CommandLine;

import javax.validation.constraints.Null;
import java.util.TimeZone;

@CommandLine.Command(name = "update_account")
public class UpdateAccountCommand extends ShowAccountCommand {

    @CommandLine.Option(names = {"-name"}, description = "The new account name", required = true)
    protected String name;

    @Override
    protected CbAccount modify(CbAccount u) {
        if(id == null) throw new NullPointerException("id cannot be null");

        CbClient client = CbClientWrapper.INSTANCE.getClient();

        return client.updateAccountName(id, new CbNameUpdateRequest(name));
    }
}
