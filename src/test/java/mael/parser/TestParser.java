package mael.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import mael.MaelException;
import mael.commands.Command;

public class TestParser {

    @Test
    public void parseInput_validTodo_success() throws MaelException {
        Command c = Parser.parseInput("todo read book");
        assert (true);
    }

    @Test
    public void parseInput_invalidTodo_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("todo"));
    }

    @Test
    public void parseInput_validDeadline_success() throws MaelException {
        Command c = Parser.parseInput("deadline submission /by 03092025 1400");
        assert (true);
    }

    @Test
    public void parseInput_deadlineMissingDeadline_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("deadline /by 03092025 1400"));
    }

    @Test
    public void parseInput_deadlineInvalidDate_throwsMaelException() {
        assertThrows(MaelException.class, 
            () -> Parser.parseInput("deadline submission /by 03092025 2500"));
    }

    @Test
    public void parseInput_deadlineInvalidFormat_throwsMaelException() {
        assertThrows(MaelException.class, 
            () -> Parser.parseInput("deadline submission /by 030920251400"));
    }

    @Test
    public void parseInput_deadlineInvalidContext_throwsMaelException() {
        assertThrows(MaelException.class, 
            () -> Parser.parseInput("deadline submission /bt 030920251400"));
    }

    @Test
    public void parseInput_validEvent_success() throws MaelException {
        Command c = Parser.parseInput("event show /from 03092025 1400 /to 05092025 1300");
        assert (true);
    }

    @Test
    public void parseInput_deadlineMissingEvent_throwsMaelException() {
        assertThrows(MaelException.class, 
            () -> Parser.parseInput("event /from 03092025 1400 /to 05092025 1300"));
    }

    @Test
    public void parseInput_eventInvalidDate_throwsMaelException() {
        assertThrows(MaelException.class, 
            () -> Parser.parseInput("event show /from 03092025 1400 /to 05092025 3300"));
    }

    @Test
    public void parseInput_eventInvalidFormat_throwsMaelException() {
        assertThrows(MaelException.class, 
            () -> Parser.parseInput("event show /to 03092025 1400 /from 05092025 1300"));
    }

    @Test
    public void parseInput_validList_success() throws MaelException {
        Command c1 = Parser.parseInput("list");
        Command c2 = Parser.parseInput("ls");
        assert (true);
    }

    @Test
    public void parseInput_listWithExtraArgs_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("list extra"));
    }

    @Test
    public void parseInput_validMark_success() throws MaelException {
        Command c = Parser.parseInput("mark 3");
        assert (true);
    }

    @Test
    public void parseInput_invalidMarkIndex_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("mark abc"));
    }

    @Test
    public void parseInput_validUnmark_success() throws MaelException {
        Command c = Parser.parseInput("unmark 2");
        Command c2 = Parser.parseInput("um 5");
        assert (true);
    }

    @Test
    public void parseInput_invalidUnmarkIndex_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("unmark xyz"));
    }

    @Test
    public void parseInput_validDelete_success() throws MaelException {
        Command c1 = Parser.parseInput("delete 7");
        Command c2 = Parser.parseInput("del 8");
        assert (true);
    }

    @Test
    public void parseInput_invalidDeleteIndex_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("delete notanumber"));
    }

    @Test
    public void parseInput_validExit_success() throws MaelException {
        Command c = Parser.parseInput("bye");
        assert (true);
    }

    @Test
    public void parseInput_exitWithExtraArgs_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("bye now"));
    }

    @Test
    public void parseInput_validFind_success() throws MaelException {
        Command c1 = Parser.parseInput("find keyword");
        Command c2 = Parser.parseInput("f something");
        assert (true);
    }

    @Test
    public void parseInput_findMissingKeyword_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("find"));
    }

    @Test
    public void parseInput_validUndo_success() throws MaelException {
        Command c = Parser.parseInput("undo");
        assert (true);
    }

    @Test
    public void parseInput_undoWithExtraArgs_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("undo please"));
    }

    @Test
    public void parseInput_validCheck_success() throws MaelException {
        Command c = Parser.parseInput("check 09092023 1200");
        assert (true);
    }

    @Test
    public void parseInput_checkInvalidDate_throwsMaelException() throws MaelException {
        assertThrows(MaelException.class, () -> Parser.parseInput("check 09092023"));
    }

    @Test
    public void parseInput_checkInvalidDate2_throwsMaelException() throws MaelException {
        assertThrows(MaelException.class, () -> Parser.parseInput("check 0909202 1322"));
    }

    @Test
    public void parseInput_checkInvalidDate3_throwsMaelException() throws MaelException {
        assertThrows(MaelException.class, () -> Parser.parseInput("check 09 09"));
    }

    @Test
    public void parseInput_checkInvalidFormat_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("check onlyonearg"));
    }

    @Test
    public void parseCommandStorage_validAdd_success() throws MaelException {
        Command c = Parser.parseCommandStorage("Add | task name |  |  | false");
        assert (true);
    }

    @Test
    public void parseCommandStorage_corruptedAdd_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseCommandStorage("Add | missing fields"));
    }

    @Test
    public void parseCommandStorage_validMark_success() throws MaelException {
        Command c = Parser.parseCommandStorage("Mark | 1");
        assert (true);
    }

    @Test
    public void parseCommandStorage_corruptedMark_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseCommandStorage("Mark | notANumber"));
    }

    @Test
    public void parseTaskStorage_validTodo_success() throws MaelException {
        Command c = Parser.parseTaskStorage("T |   | sample todo");
        assert (true);
    }

    @Test
    public void parseTaskStorage_corruptedDeadline_throwsMaelException() {
        assertThrows(MaelException.class,
                () -> Parser.parseTaskStorage("D | X | missing date"));
    }

    @Test
    public void parseInput_unknownCommand_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("foobar something"));
    }
}
