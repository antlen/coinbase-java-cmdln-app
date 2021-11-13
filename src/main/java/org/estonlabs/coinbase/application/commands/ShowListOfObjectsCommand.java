package org.estonlabs.coinbase.application.commands;

import picocli.CommandLine;

import java.util.List;

public abstract class ShowListOfObjectsCommand<T> implements Runnable{
    private static final String DELIMITER = " | ";

    @CommandLine.Option(names = {"-all"}, description = "Shows all of the accounts. By default will only show the accounts with a balance.")
    Boolean all;

    @CommandLine.Option(names = {"-verbose"}, description = "Show all details")
    Boolean verbose;

    protected abstract List<T> getData();

    protected abstract boolean shouldDisplay(T t);

    protected abstract String[] summarizeFields(T t);

    @Override
    public final void run() {
        List<T> data = getData();
        boolean showAll = (all !=null && all);
        boolean verbose = (this.verbose !=null && this.verbose);
        for(T d : data){
            if(showAll ||(!showAll && shouldDisplay(d))){
                if(verbose){
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
    }
}
