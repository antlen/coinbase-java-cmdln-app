package com.coinbase.application.commands.user;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.CoinbaseCallback;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.client.CoinbaseRestClient;
import com.coinbase.domain.user.CbUser;
import com.coinbase.domain.user.response.CbUserResponse;
import picocli.CommandLine;

@CommandLine.Command(name = "show",
        description = "shows the user detail.  Will default to current user if -id is not passed.",
        mixinStandardHelpOptions = true)
public class ShowUserCommand extends ShowObjectCommand<CbUserResponse> {
    @CommandLine.Option(names = {"-id"}, description = "The user id.")
    protected String id;
    @CommandLine.Option(names = {"-auth"}, description = "Whether to display the Oath info.  If omitted the users basic info will be displayed.")
    boolean auth;

    @Override
    protected CbUserResponse getData(CoinbaseRestClient client) {
        return id == null ? client.getUser() : client.getUser(id);
    }

    @Override
    protected void fetchData(CoinbaseAsyncRestClient c, CoinbaseCallback<CbUserResponse> cb) {
        if(id == null){
            c.fetchUser(cb);
        } else c.fetchUser(cb, id);

    }

    @Override
    protected String[] summarizeFields(CbUserResponse u) {
        CbUser data = u.getData();
        return new String[]{data.getId(), data.getName()};
    }
}
