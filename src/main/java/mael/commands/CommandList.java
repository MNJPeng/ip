package mael.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.ui.UI;

public class CommandList {
    
    private final List<Command> commands = new ArrayList<>();

    /**
     * Default constructor for {@code CommandList}
     * 
     * @param storage Storage used to load saved commands
     * @param ui UI used for Mael instance
     */
    public CommandList(Storage storage, UI ui) {
        storage.load().forEach(text -> {
            try {
                Command c = Parser.parseCommandStorage(text);
                commands.add(c);
            } catch (MaelException e) {
                ui.printException(e);
            }
        });
    }

    /**
     * Returns a list of Strings which represent {@code Command} to save
     *
     * @return List of Strings
     */
    public List<String> getCommandsAsStrings() {
        return commands.stream().map(Command::toString).collect(Collectors.toList());
    }

    /**
     * Adds a command to the list of commands if it is not already in the list
     * 
     * @param command Command to add
     * @return The command added unless it already exists, in which case null is returned
     */
    public Command addCommandtoList(Command command) {
        if (!commands.contains(command)) {
            commands.add(command);
            return command;
        } else {
            return null;
        }
    }

    /**
     * Removes a command from the list of commands if it exists in the list
     * 
     * @param command Command to remove
     * @return The command removed unless it does not exist, in which case null is returned
     */
    public Command removeCommand(Command command) {
        if (commands.contains(command)) {
            commands.remove(command);
            return command;
        } else {
            return null;
        }
    }

    /**
     * Returns the last command in the list of commands
     * 
     * @return The last command in the list of commands, or null if the list is empty
     */
    public Command getLastCommand() {
        if (!commands.isEmpty()) {
            return commands.get(commands.size() - 1);
        } else {
            return null;
        }
    }
}
