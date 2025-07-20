package ru.T1Debut.util;

public class CommandTask implements Runnable{
    public final Runnable command;

    public CommandTask(Runnable command){
        this.command = command;
    }

    @Override
    public void run(){
        command.run();
    }
}
