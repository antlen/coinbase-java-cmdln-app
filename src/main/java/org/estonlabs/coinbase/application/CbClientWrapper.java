package org.estonlabs.coinbase.application;

import org.estonlabs.coinbase.client.CbClient;
import org.estonlabs.coinbase.client.CbClientFactory;

public class CbClientWrapper {
    public static final CbClientWrapper INSTANCE = new CbClientWrapper();
    private CbClient client;

    private CbClientWrapper(){
    }

    public void init(String apiKey, byte[] secret, boolean useSandbox, String passphrase){
        client = CbClientFactory.newCbRestApiClient(apiKey, secret, useSandbox, passphrase);
    }

    public CbClient getClient() {
        return client;
    }
}
