package org.estonlabs.coinbase.application.commands;

import org.estonlabs.coinbase.CbApiException;
import picocli.CommandLine;

public abstract class ShowObjectCommand<T> implements Runnable {
    private static final String DELIMITER = " | ";
    private final boolean idIsRequired;

    @CommandLine.Option(names = {"-id"}, description = "The id of the object to display")
    protected String id;

    @CommandLine.Option(names = {"-verbose"}, description = "Show all details")
    Boolean verbose;
    public ShowObjectCommand() {
        this(false);
    }

    public ShowObjectCommand(boolean idIsRequired) {
        this.idIsRequired = idIsRequired;
    }

    protected abstract T getData();

    protected abstract String[] summarizeFields(T t);

    protected T modify(T t){
        return t;
    }

    @Override
    public final void run() {
        if(idIsRequired && id == null){
            throw new NullPointerException("id cannot be null");
        }
        try {
            T d = modify(getData());
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
            e.printStackTrace();
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
