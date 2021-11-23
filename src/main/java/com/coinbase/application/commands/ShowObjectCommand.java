package com.coinbase.application.commands;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.client.CoinbaseClient;
import com.coinbase.exception.CbApiException;
import picocli.CommandLine;

public abstract class ShowObjectCommand<T> implements Runnable {
    private static final String DELIMITER = " | ";

    @CommandLine.Option(names = {"-verbose"}, description = "Show all details")
    Boolean verbose;

    protected abstract T getData(CoinbaseClient c);

    protected abstract String[] summarizeFields(T t);

    protected T modify(T t){
        return t;
    }

    @Override
    public final void run() {
        try {
            T d = modify(getData(CbClientWrapper.INSTANCE.getClient()));
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

        }catch(CbApiException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
