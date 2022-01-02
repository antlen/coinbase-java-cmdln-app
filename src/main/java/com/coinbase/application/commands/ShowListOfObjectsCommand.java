package com.coinbase.application.commands;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.client.async.CoinbaseASyncClient;
import picocli.CommandLine;

import java.util.Collection;

public abstract class ShowListOfObjectsCommand<T> implements Runnable{
    private static final String DELIMITER = " | ";

    @CommandLine.Option(names = {"-all"}, description = "Shows all of the results. By default will only show the results if shouldDisplay is true")
    protected Boolean all;

    @CommandLine.Option(names = {"-verbose"}, description = "Show all details")
    protected Boolean verbose;

    @CommandLine.Option(names = {"-filter"}, description = "Filter using this value.")
    protected String filter;

    protected abstract void fetchData(CoinbaseASyncClient c, CommandCallback<T> cb);

    protected abstract boolean shouldDisplay(T t);

    protected abstract String[] summarizeFields(T t);

    @Override
    public final void run() {
        fetchData(CbClientWrapper.INSTANCE.getAsyncClient(),new CommandCallback<T>(){
            @Override
            public void response(Collection<T> data, final int count) {
                boolean showAll = (all !=null && all);
                boolean isVerbose = (verbose !=null && verbose);
                for(T d : data){
                    if(showAll || shouldDisplay(d)){
                        if(isVerbose){
                            System.out.println("("+count + ")" +d);
                        }else{
                            StringBuilder b = new StringBuilder();
                            for(String f : summarizeFields(d)){
                                b.append(DELIMITER).append(f);
                            }
                            System.out.println("("+count + ")" +b);
                        }
                    }
                }
            }
        });
    }

}
