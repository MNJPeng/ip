package mael.parser;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        assertThrows(MaelException.class, () -> Parser.parseInput("deadline submission /by 03092025 2500"));
    }

    @Test
    public void parseInput_deadlineInvalidFormat_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("deadline submission /by 030920251400"));
    }

    @Test
    public void parseInput_deadlineInvalidContext_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("deadline submission /bt 030920251400"));
    }

    @Test
    public void parseInput_validEvent_success() throws MaelException {
        Command c = Parser.parseInput("event show /from 03092025 1400 /to 05092025 1300");
        assert (true);
    }

    @Test
    public void parseInput_deadlineMissingEvent_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("event /from 03092025 1400 /to 05092025 1300"));
    }

    @Test
    public void parseInput_eventInvalidDate_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("event show /from 03092025 1400 /to 05092025 3300"));
    }

    @Test
    public void parseInput_eventInvalidFormat_throwsMaelException() {
        assertThrows(MaelException.class, () -> Parser.parseInput("event show /to 03092025 1400 /from 05092025 1300"));
    }
}
