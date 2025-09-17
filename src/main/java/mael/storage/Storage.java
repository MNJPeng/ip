package mael.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import mael.tasklist.TaskList;

public class Storage {

    private final String filePath;
    private final String[] pathSegments;
    private final File taskFile;

    /**
     * Default constructor for {@code Storage}
     * 
     * @param filePath Path of file storing tasks
     * @param UI UI used for Mael instance
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        pathSegments = filePath.split("/");
        taskFile = new File("./" + filePath);
    }

    /**
     * Returns saved tasks as a list of strings
     * 
     * @return List of tasks as strings
     */
    public ArrayList<String> load() {
        ArrayList<String> tasks = new ArrayList<>();

        if (!buildFile()) {
            return tasks;
        }

        try {
            Scanner taskReader = new Scanner(taskFile);
            while (taskReader.hasNextLine()) {
                String currLine = taskReader.nextLine();
                tasks.add(currLine);
            }
            taskReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);  //Shouldn't be possible
        } finally {
            try {
                taskFile.delete();
                taskFile.createNewFile();
            } catch (IOException e) {
                System.err.println(e);  //Shouldn't happen
            }
        }

        return tasks;
    }

    /**
     * Saved tasks from a list of strings
     * 
     * @param tasks List of tasks as strings
     */
    public void save(TaskList tasks) {

        buildFile();

        try {
            FileWriter taskWriter = new FileWriter("./" + filePath);
            for (String task : tasks.getTasksAsSaveStrings()) {
                taskWriter.write(task);
            }
            taskWriter.close();
        } catch (IOException e) {
            System.err.println(e);  //Shouldn't happen
        }
    }

    private boolean buildFile() {
        for (int i = 0; i < pathSegments.length - 1; i++) {
            String pathName = ".";
            for (int j = 0; j <= i; j++) {
                pathName += "/" + pathSegments[j];
            }
            File taskFolder = new File(pathName);
            if (!taskFolder.exists()) {
                taskFolder.mkdir();
            } else if (taskFolder.isFile()) {
                taskFolder.delete();
                taskFolder.mkdir();
            }
        }

        try {
            if (!taskFile.exists()) {
                taskFile.createNewFile();
            } else if (taskFile.isDirectory()) {
                taskFile.delete();
                taskFile.createNewFile();
            }
        } catch (IOException e) {
            return false;  //Shouldn't happen
        }

        return true;
    }
}
