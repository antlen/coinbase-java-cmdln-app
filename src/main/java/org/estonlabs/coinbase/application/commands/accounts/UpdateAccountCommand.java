package org.estonlabs.coinbase.application.commands.accounts;

import org.estonlabs.coinbase.application.CbClientWrapper;
import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.domain.CbNameUpdateRequest;
import org.estonlabs.coinbase.domain.account.CbAccount;
import picocli.CommandLine;

@CommandLine.Command(name = "update",  description = "updates an account for the given id.",
        mixinStandardHelpOptions = true)
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
