package edu.du.ict4315.commands;


import java.util.Properties;

public interface Command {

    public String getCommandName();
    public String getDisplayName();
    public String execute(Properties params);
}
