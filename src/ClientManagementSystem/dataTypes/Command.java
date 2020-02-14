package Ex6Task2Files.dataTypes;

import java.io.Serializable;

public class Command implements Serializable {
    private String commandType, searchArg;
    private static final long serialVersionUID = 2L;

    public Command(String commandType, String searchArg){
        this.commandType = commandType;
        this.searchArg = searchArg;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getSearchArg() {
        return searchArg;
    }
}
