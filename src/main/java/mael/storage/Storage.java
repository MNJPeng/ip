package mael.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import mael.taskList.TaskList;

public class Storage {

    private final String filePath;

    /**
     * Default constructor for {@code Storage}
     * 
     * @param filePath Path of file storing tasks
     * @param UI UI used for Mael instance
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Returns saved tasks as a list of strings
     * 
     * @return List of tasks as strings
     */
    public ArrayList<String> load() {
        String[] pathSegments = filePath.split("/");
        ArrayList<String> tasks = new ArrayList<>();

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

        File taskFile = new File("./" + filePath);
        try {
            if (!taskFile.exists()) {
                taskFile.createNewFile();
            } else if (taskFile.isDirectory()) {
                taskFile.delete();
                taskFile.createNewFile();
            }
            Scanner taskReader = new Scanner(taskFile);
            while (taskReader.hasNextLine()) {
                String currLine = taskReader.nextLine();
                tasks.add(currLine);
                
            }
            taskReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);  //Shouldn't be possible
        } catch (IOException e) {
            System.err.println(e);  //Shouldn't happen
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

    public void save(TaskList tasks) {
        try {
            FileWriter taskWriter = new FileWriter("./" + filePath);
            for (String task : tasks.getTasksSave()) {
                taskWriter.write(task);
            }
            taskWriter.close();
        } catch (IOException e) {
            System.err.println(e);  //Shouldn't happen
        }
    }



}
