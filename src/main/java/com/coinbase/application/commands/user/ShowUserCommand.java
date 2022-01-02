package com.coinbase.application.commands.user;

import com.coinbase.application.commands.ShowObjectCommand;
import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.domain.user.CbUser;
import picocli.CommandLine;

@CommandLine.Command(name = "show",
        description = "shows the user detail.  Will default to current user if -id is not passed.",
        mixinStandardHelpOptions = true)
public class ShowUserCommand extends ShowObjectCommand<CbUser> {
    @CommandLine.Option(names = {"-id"}, description = "The user id.")
    protected String id;
    @CommandLine.Option(names = {"-auth"}, description = "Whether to display the Oath info.  If omitted the users basic info will be displayed.")
    boolean auth;

    @Override
    protected CbUser getData(CoinbaseSyncClient client) {
        return id == null ? client.getUser() : client.getUser(id);
    }

    @Override
    protected void fetchData(CoinbaseASyncClient c, ResponseCallback<CbUser> cb) {
        if(id == null){
            c.fetchUser(cb);
        } else c.fetchUser(cb, id);
    }

    @Override
    protected String[] summarizeFields(CbUser u) {
        return new String[]{u.getId(), u.getName()};
    }
}
