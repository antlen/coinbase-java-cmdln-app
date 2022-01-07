package com.coinbase.application.commands;

import com.coinbase.application.client.CbClientWrapper;
import com.coinbase.client.CoinbaseAsyncRestClient;
import com.coinbase.domain.general.response.ResponseBody;
import picocli.CommandLine;

import java.util.Collection;
import java.util.List;

public abstract class ShowListOfObjectsCommand<D, T extends ResponseBody<List<D>>> implements Runnable{
    private static final String DELIMITER = " | ";

    @CommandLine.Option(names = {"-all"}, description = "Shows all of the results. By default will only show the results if shouldDisplay is true")
    protected Boolean all;

    @CommandLine.Option(names = {"-verbose"}, description = "Show all details")
    protected Boolean verbose;

    @CommandLine.Option(names = {"-filter"}, description = "Filter using this value.")
    protected String filter;

    protected abstract void fetchData(CoinbaseAsyncRestClient c, CommandCallback<T> cb);

    protected abstract boolean shouldDisplay(D t);

    protected abstract String[] summarizeFields(D t);

    @Override
    public final void run() {
        fetchData(CbClientWrapper.INSTANCE.getAsyncClient(),new CommandCallback<T>(){
            @Override
            public void response(T response, final int count) {
                Collection<D> data = response.getData();
                boolean showAll = (all !=null && all);
                boolean isVerbose = (verbose !=null && verbose);
                for(D d : data){
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
