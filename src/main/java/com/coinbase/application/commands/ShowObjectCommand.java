package com.coinbase.application.commands;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.callback.ResponseCallback;
import com.coinbase.client.async.CoinbaseASyncClient;
import com.coinbase.client.sync.CoinbaseSyncClient;
import com.coinbase.exception.CbApiException;
import picocli.CommandLine;

public abstract class ShowObjectCommand<T> implements Runnable {
    private static final String DELIMITER = " | ";

    @CommandLine.Option(names = {"-verbose"}, description = "Show all details")
    Boolean verbose;

    @CommandLine.Option(names = {"-sync"}, description = "Request using a synchronized call.")
    boolean sync;

    protected abstract T getData(CoinbaseSyncClient c);

    protected abstract void fetchData(CoinbaseASyncClient c, ResponseCallback<T> cb);

    protected abstract String[] summarizeFields(T t);

    protected T modify(T t){
        return t;
    }

    @Override
    public final void run() {
        try{
            if(sync) {
                sync();
            }else{
                async();
            }
        }catch(CbApiException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void sync() {
        T data = getData(CbClientWrapper.INSTANCE.getClient());
        print(data);
    }

    private void async() {
        fetchData(CbClientWrapper.INSTANCE.getAsyncClient(), new ResponseCallback<T>() {
            @Override
            public void completed(T t) {
                print(t);
            }

            @Override
            public void failed(Throwable e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        });
    }

    private void print(T data) {
        T d = modify(data);
        boolean printAll = (verbose !=null && verbose);

        if(printAll){
            System.out.println(d);
        }else{
            StringBuilder b = new StringBuilder();
            for(String f : summarizeFields(d)){
                b.append(DELIMITER).append(f);
            }
            System.out.println(b.toString());
        }
    }
}
