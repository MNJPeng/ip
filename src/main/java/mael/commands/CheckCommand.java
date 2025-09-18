package mael.commands;

import java.time.LocalDateTime;

import mael.MaelException;
import mael.parser.Parser;
import mael.storage.Storage;
import mael.tasklist.TaskList;
import mael.ui.UI;

public class CheckCommand extends Command {
    
    private final LocalDateTime DATE_BY;

    /**
     * Default constructor for CheckCommand
     * 
     * @param dateBy Date to check whether in event durations, or after deadlines
     */
    public CheckCommand(String dateBy) {
        this.DATE_BY = Parser.parseDate(dateBy);
    }

    @Override
    public void execute(TaskList taskList, Storage taskStorage, UI ui) {
        ui.printCheckHeader(DATE_BY);
        ui.printList(taskList.checkDate(DATE_BY));
    }

    @Override
    public String executeReturnString(CommandList commandList, Storage commandStorage, 
        TaskList taskList, Storage taskStorage, UI ui) {
        String response = "";
        response += ui.getCheckHeaderString(DATE_BY);
        response += ui.getListString(taskList.checkDate(DATE_BY));
        return response;
    }

    @Override
    public String undoReturnString(CommandList commandList, Storage commandStorage,
        TaskList taskList, Storage taskStorage, UI ui) throws MaelException {
        throw new MaelException("Check command cannot be undone");
    }

    @Override
    public String toString() {
        return "Check | " + DATE_BY.format(Parser.USER_FORMAT);
    }
}
