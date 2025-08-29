package mael.parser;

import java.time.format.DateTimeFormatter;

import mael.MaelException;
import mael.commands.AddCommand;
import mael.commands.CheckCommand;
import mael.commands.Command;
import mael.commands.DeleteCommand;
import mael.commands.ListCommand;
import mael.commands.MarkCommand;
import mael.commands.UnmarkCommand;
import mael.taskList.TaskList.Task.Deadline;
import mael.taskList.TaskList.Task.Event;
import mael.taskList.TaskList.Task.ToDo;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Parser {
    
    public static final DateTimeFormatter USER_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");
    public static final DateTimeFormatter PRINT_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
    
    public static Command parse(String text) throws MaelException {
        String[] sections = text.split(" /");
        String[] commandType = sections[0].split(" ", 2);
        switch (commandType[0]) {
            case "event" -> {
                if (commandType.length == 1) {
                    throw new MaelException("Event activity unspecified");
                } else if (sections.length != 3) {
                    throw new MaelException("Event details unclear");
                } else if (!sections[1].substring(0, 4).equals("from") 
                        || !sections[2].substring(0, 2).equals("to")) {
                    throw new MaelException("Event boundaries unclear");
                }
                return new AddCommand(commandType[1], sections[1].substring(6), sections[2].substring(4));
            }
            case "deadline" -> {
                if (commandType.length == 1) {
                    throw new MaelException("Deadline activity unspecified");
                } else if (sections.length != 2) {
                    throw new MaelException("Deadline details unclear");
                } else if (!sections[1].substring(0, 2).equals("by")) {
                    throw new MaelException("Deadline unclear");
                }
                return new AddCommand(commandType[1], sections[1].substring(4));
            }
            case "todo" -> {
                if (commandType.length == 1) {
                    throw new MaelException("ToDo activity unspecified");
                } else if (sections.length != 1) {
                    throw new MaelException("ToDo details unclear");
                }
                return new AddCommand(commandType[1]);
            }
            case "list", "ls" -> {
                if (commandType.length == 1) {
                    return new ListCommand();
                    // System.out.println("\t\t-Outstanding Missions-");
                    // for (int i = 0; i < tasks.size(); i++) {
                    //     System.out.println("\t" + (i + 1) + "." + tasks.get(i));
                    // }
                } else {
                    throw new MaelException("Unknown command for list");
                }
            }
            case "mark", "m" -> {
                if (commandType.length == 2) {
                    try {
                        return new MarkCommand(Integer.parseInt(commandType[1]));
                        // int taskNum = Integer.parseInt(input.split(" ")[1]);
                        // tasks.get(taskNum - 1).markComplete();
                        // System.out.println("\t" + tasks.get(taskNum - 1));
                        // System.out.println("\t\t-Mission Completed-");
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
                if (commandType.length == 2) {
                    try {
                        return new UnmarkCommand(Integer.parseInt(commandType[1]));
                        // int taskNum = Integer.parseInt(input.split(" ")[1]);
                        // tasks.get(taskNum - 1).markIncomplete();
                        // System.out.println("\t" + tasks.get(taskNum - 1));
                        // System.out.println("\t\t-Mission Unsuccessful-");
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
                if (commandType.length == 2) {
                    try {
                        return new DeleteCommand(Integer.parseInt(commandType[1]));
                        // int taskNum = Integer.parseInt(input.split(" ")[1]);
                        // System.out.println("\t" + tasks.get(taskNum - 1));
                        // tasks.remove(taskNum - 1);
                        // System.out.println("\t\t-Mission Terminated-");
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
                        return new CheckCommand(LocalDateTime.parse(commandType[1], userFormat));
                        // LocalDateTime dateby = LocalDateTime.parse(input.split(" ", 2)[1], userFormat);
                        // int count = 0;
                        // System.out.println("\t\t-Missions by " + dateby.format(printFormat) + "-");
                        // for (Task task : tasks) {
                        //     if (task.isBefore(dateby)) {
                        //         count++;
                        //         System.out.println("\t" + count + "." + task);
                        //     }
                        // }
                    } catch (DateTimeException e) {
                        throw new MaelException("Date invalid");
                    }
                } else {
                    throw new MaelException("Unknown command for check");
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
        return LocalDateTime.parse(dateTime,USER_FORMAT);
    }

    public static Command parseStorage(String text) throws MaelException {
        String[] sections = text.split(" \\| ");
            try {
                switch (sections[0]) {
                    case "T":
                        if (sections.length == 3) {
                            return new AddCommand(sections[2], 
                                sections[1].equals("X"));
                        } else {
                            throw new MaelException("Corrupted ToDo");
                        }
                    case "D":
                        if (sections.length == 4) {
                            return new AddCommand(sections[2], 
                                sections[3], 
                                sections[1].equals("X"));
                        } else {
                            throw new MaelException("Corrupted Deadline");
                        }
                    case "E":
                        if (sections.length == 5) {
                            return new AddCommand(sections[2], 
                                sections[3], 
                                sections[4], 
                                sections[1].equals("X"));
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
