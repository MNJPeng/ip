package mael;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Storage {

    private final String filePath;
    private final UI ui;

    /**
     * Default constructor for {@code Storage}
     * 
     * @param filePath Path of file storing tasks
     * @param UI UI used for Mael instance
     */
    public Storage(String filePath, UI ui) {
        this.filePath = filePath;
        this.ui = ui;
    }


    /**
     * Returns 
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

}
