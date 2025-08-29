package mael;

import java.time.format.DateTimeFormatter;

import mael.ui.UI;

import mael.taskList.TaskList;

import mael.storage.Storage;

public class Mael {



    public void run() {
        UI ui = new UI(true, true);
        Storage storage = new Storage("data/Mael");
        TaskList taskList = new TaskList(storage);

        ui.launch();

        boolean isExit = false;

        
        while (!isExit) {  // Early termination when "bye" is input
            ui.line();
            // switch (input.split(" ")[0]) {  // Switch only checking for the first word
            //     case "list", "ls" -> {
            //         if (input.split(" ").length == 1) {
            //             System.out.println("\t\t-Outstanding Missions-");
            //             for (int i = 0; i < tasks.size(); i++) {
            //                 System.out.println("\t" + (i + 1) + "." + tasks.get(i));
            //             }
            //         } else {
            //             System.err.println(new MaelException("Unknown command for list"));
            //         }
            //     }
            //     case "mark", "m" -> {
            //         if (input.split(" ").length == 2) {
            //             try {
            //                 int taskNum = Integer.parseInt(input.split(" ")[1]);
            //                 tasks.get(taskNum - 1).markComplete();
            //                 System.out.println("\t" + tasks.get(taskNum - 1));
            //                 System.out.println("\t\t-Mission Completed-");
            //             } catch (NumberFormatException e) {
            //                 System.err.println(new MaelException("Mark details unclear"));
            //             } catch (IndexOutOfBoundsException e) {
            //                 System.err.println(new MaelException("Mission unspecified"));
            //             } catch (MaelException e) {
            //                 System.err.println(e);
            //             }
            //         } else {
            //             System.err.println(new MaelException("Unknown command for mark"));
            //         }
            //     }
            //     case "unmark", "um" -> {
            //         if (input.split(" ").length == 2) {
            //             try {
            //                 int taskNum = Integer.parseInt(input.split(" ")[1]);
            //                 tasks.get(taskNum - 1).markIncomplete();
            //                 System.out.println("\t" + tasks.get(taskNum - 1));
            //                 System.out.println("\t\t-Mission Unsuccessful-");
            //             } catch (NumberFormatException e) {
            //                 System.err.println(new MaelException("Unmark details unclear"));
            //             } catch (IndexOutOfBoundsException e) {
            //                 System.err.println(new MaelException("Mission unspecified"));
            //             } catch (MaelException e) {
            //                 System.err.println(e);
            //             }
            //         } else {
            //             System.err.println(new MaelException("Unknown command for unmark"));
            //         } 
            //     }
            //     case "delete", "del" -> {
            //         if (input.split(" ").length == 2) {
            //             try {
            //                 int taskNum = Integer.parseInt(input.split(" ")[1]);
            //                 System.out.println("\t" + tasks.get(taskNum - 1));
            //                 tasks.remove(taskNum - 1);
            //                 System.out.println("\t\t-Mission Terminated-");
            //             } catch (NumberFormatException e) {
            //                 System.err.println(new MaelException("Termination details unclear"));
            //             } catch (IndexOutOfBoundsException e) {
            //                 System.err.println(new MaelException("Mission unspecified"));
            //             }
            //         } else {
            //             System.err.println(new MaelException("Unknown command for delete"));
            //         } 
            //     }
            //     case "check", "ch" -> {
            //         if (input.split(" ").length == 3) {
            //             try {
            //                 LocalDateTime dateby = LocalDateTime.parse(input.split(" ", 2)[1], userFormat);
            //                 int count = 0;
            //                 System.out.println("\t\t-Missions by " + dateby.format(printFormat) + "-");
            //                 for (Task task : tasks) {
            //                     if (task.isBefore(dateby)) {
            //                         count++;
            //                         System.out.println("\t" + count + "." + task);
            //                     }
            //                 }
            //             } catch (DateTimeException e) {
            //                 System.err.println(new MaelException("Date invalid"));
            //             }
            //         } else {
            //             System.err.println(new MaelException("Unknown command for check"));
            //         } 
            //     }
            //     default -> {
            //         try {
            //             tasks.add(Task.of(input));
            //             System.out.println("\t>>> " + tasks.get(tasks.size() - 1));
            //             System.out.println("\t\t-Mael Acknowleged-");
            //         } catch (MaelException e) {
            //             System.err.println(e);
            //         } catch (DateTimeException e) {
            //             System.err.println(new MaelException("Date invalid"));
            //         } 
            //     }
            // }
            ui.line();
            input = SCANNER.nextLine();
        }

        ui.close();
    }

    public static void main(String[] args) throws InterruptedException {
        
    }
}
