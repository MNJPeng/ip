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
}
