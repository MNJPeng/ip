package mael.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import mael.MaelException;
import mael.commands.AddCommand;
import mael.commands.CheckCommand;
import mael.commands.Command;
import mael.commands.DeleteCommand;
import mael.commands.ExitCommand;
import mael.commands.ListCommand;
import mael.commands.MarkCommand;
import mael.commands.UnmarkCommand;

public class Parser {

    public static final DateTimeFormatter USER_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");
    public static final DateTimeFormatter PRINT_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");

    /**
     * Parses inputs from the user in the format required
     *
     * @param text User input
     * @return {@code Command} based on user request
     * @throws MaelException If user input is not in required format
     */
    public static Command parseInput(String text) throws MaelException {
        String[] sections = text.split(" /");
        String[] commandSections = sections[0].split(" ", 2);
        switch (commandSections[0]) {
            case "event" -> {
                if (commandSections.length == 1) {
                    throw new MaelException("Event activity unspecified");
                } else if (sections.length != 3) {
                    throw new MaelException("Event details unclear");
                } else if (!sections[1].substring(0, 4).equals("from")
                        || !sections[2].substring(0, 2).equals("to")
                        || sections[1].substring(5).length() != 13
                        || sections[2].substring(3).length() != 13) {
                    throw new MaelException("Event boundaries unclear");
                }
                return new AddCommand(commandSections[1], sections[1].substring(5), sections[2].substring(3), false, true);
            }
            case "deadline" -> {
                if (commandSections.length == 1) {
                    throw new MaelException("Deadline activity unspecified");
                } else if (sections.length != 2) {
                    throw new MaelException("Deadline details unclear");
                } else if (!sections[1].substring(0, 2).equals("by")
                        || sections[1].substring(3).length() != 13) {
                    throw new MaelException("Deadline unclear");
                }
                return new AddCommand(commandSections[1], sections[1].substring(3), false, true);
            }
            case "todo" -> {
                if (commandSections.length == 1) {
                    throw new MaelException("ToDo activity unspecified");
                } else if (sections.length != 1) {
                    throw new MaelException("ToDo details unclear");
                }
                return new AddCommand(commandSections[1], false, true);
            }
            case "list", "ls" -> {
                if (commandSections.length == 1) {
                    return new ListCommand();
                } else {
                    throw new MaelException("Unknown command for list");
                }
            }
            case "mark", "m" -> {
                if (commandSections.length == 2) {
                    try {
                        return new MarkCommand(Integer.parseInt(commandSections[1]));
                    } catch (NumberFormatException e) {
                        throw new MaelException("Mark details unclear");
                    } catch (IndexOutOfBoundsException e) {
                        throw new MaelException("Mission unspecified");
                    }
                } else {
                    throw new MaelException("Unknown command for mark");
                }
            }
            case "unmark", "um" -> {
                if (commandSections.length == 2) {
                    try {
                        return new UnmarkCommand(Integer.parseInt(commandSections[1]));
                    } catch (NumberFormatException e) {
                        throw new MaelException("Unmark details unclear");
                    } catch (IndexOutOfBoundsException e) {
                        throw new MaelException("Mission unspecified");
                    }
                } else {
                    throw new MaelException("Unknown command for unmark");
                }
            }
            case "delete", "del" -> {
                if (commandSections.length == 2) {
                    try {
                        return new DeleteCommand(Integer.parseInt(commandSections[1]));
                    } catch (NumberFormatException e) {
                        throw new MaelException("Termination details unclear");
                    } catch (IndexOutOfBoundsException e) {
                        throw new MaelException("Mission unspecified");
                    }
                } else {
                    throw new MaelException("Unknown command for delete");
                }
            }
            case "check", "ch" -> {
                if (text.split(" ").length == 3) {
                    try {
                        return new CheckCommand(LocalDateTime.parse(commandSections[1], USER_FORMAT));
                    } catch (DateTimeException e) {
                        throw new MaelException("Date invalid");
                    }
                } else {
                    throw new MaelException("Unknown command for check");
                }
            }
            case "bye" -> {
                if (text.split(" ").length == 1) {
                    return new ExitCommand();
                } else {
                    throw new MaelException("Unknown command for bye");
                }
            }
            default -> {
                throw new MaelException("Unknown Mission");
            }
        }
    }

    /**
     * Returns LocalDateTime from a string of the date and time
     *
     * @param dateTime String in ddMMyyyy HHmm format
     * @return LocalDateTime format of string
     * @throws DateTimeException If string cannot be parsed in the format
     */
    public static LocalDateTime parseDate(String dateTime) throws DateTimeException {
        return LocalDateTime.parse(dateTime, USER_FORMAT);
    }

    /**
     * Parses tasks stored in storage
     *
     * @param text Stored task
     * @return {@code AddCommand} based on stored task
     * @throws MaelException If task in storage was corrupted
     */
    public static Command parseStorage(String text) throws MaelException {
        String[] sections = text.split(" \\| ");
        try {
            switch (sections[0]) {
                case "T":
                    if (sections.length == 3) {
                        return new AddCommand(sections[2],
                                sections[1].equals("X"), false);
                    } else {
                        throw new MaelException("Corrupted ToDo");
                    }
                case "D":
                    if (sections.length == 4) {
                        return new AddCommand(sections[2],
                                sections[3],
                                sections[1].equals("X"), false);
                    } else {
                        throw new MaelException("Corrupted Deadline");
                    }
                case "E":
                    if (sections.length == 5) {
                        return new AddCommand(sections[2],
                                sections[3],
                                sections[4],
                                sections[1].equals("X"), false);
                    } else {
                        throw new MaelException("Corrupted Event");
                    }
                default:
                    throw new MaelException("Unable to load unknown task");
            }
        } catch (DateTimeException e) {
            throw new MaelException("Date corrupted");
        }
    }
}
