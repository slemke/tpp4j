package de.fhkoeln;

import de.fhkoeln.datatypes.Document;
import de.fhkoeln.datatypes.Image;
import de.fhkoeln.datatypes.Text;
import de.fhkoeln.exceptions.PipelineException;
import de.fhkoeln.io.TextReader;
import de.fhkoeln.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides access to the <code>Pipeline</code> that uses different task to edit the documents.
 * Currently it can read *.jpeg, *.tiff and *.txt.
 */
public class Pipeline {

    private static final Logger log = Logger.getLogger(Pipeline.class.getName());
    private LinkedList<Task> tasks = new LinkedList<Task>();
    private LinkedList<Document> documents = new LinkedList<Document>();
    private String workingDirectory;

    /**
     * Creates a new <code>Pipeline</code>.
     * @param workingDirectory The working directory that every <code>Task</code> in this <code>Pipeline</code> uses.
     */
    public Pipeline(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    /**
     * Assigns a new <code>Task</code> to the <code>Pipeline</code>.
     * @param task the new <code>Task</code>
     * @return
     */
    public void addTask(Task task) {
        task.setWorkingDirectory(getWorkingDirectory());
        tasks.add(task);
    }

    /**
     * Removes a <code>Task</code> from the <code>Pipeline</code>.
     * @param task The <code>Task</code> that is going to be removed.
     * @return
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Checks if the <code>Pipeline</code> contains a certain <code>Task</code>.
     * @param task The <code>Task</code> in question
     * @return True if it contains the <code>Task</code>, else it will return False
     */
    public boolean containsTask(Task task) {
        return tasks.contains(task);
    }

    /**
     * Removes all <code>Task</code>s from the <code>Pipeline</code>.
     */
    public void clearTasks() {
        tasks.clear();
    }

    /**
     * Returns the number of <code>Task</code>s currently in the <code>Pipeline</code>.
     * @return The number of <code>Task</code>s in the <code>Pipeline</code>.
     */
    public int numberOfTasks() {
        return tasks.size();
    }

    /**
     * Adds a new <code>Document</code> to the <code>Pipeline</code>.
     * @param path The path to the directory containing the <code>Document</code>.
     */
    public void addDocument(String path) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            Text text = new Text();
            text.setFileName(directory.getName());

            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        try {
                            String type = Files.probeContentType(file.toPath());
                            if (type.equals("image/tiff")) {
                                text.addImage(new Image(file));
                            }
                            if (type.equals("image/jpeg")) {
                                text.addImage(new Image(file));
                            }
                        } catch (IOException e) {
                            getLogger().log(Level.SEVERE, "Couldn't add document to queue.", e);
                        }
                    }
                }
                documents.add(text);
            } else {
                getLogger().log(Level.WARNING, "Couldn't add document " + directory.getName() + " to the pipeline");
            }
        } else {
            try {
                String type = Files.probeContentType(directory.toPath());
                if (type.equals("image/tiff") || type.equals("image/jpeg"))
                    documents.add(new Image(directory));

                if(type.equals("text/plain")) {
                    TextReader reader = new TextReader();
                    String read = reader.read(directory);
                    Text text = new Text();
                    text.setText(read);
                    String name = directory.getName();
                    text.setFileName(name.substring(0, name.indexOf(".")));
                    documents.add(text);
                }
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Couldn't add document to queue.", e);
            }
        }
    }

    /**
     * Removes a <code>Document</code> from the <code>Pipeline</code>.
     * @param document The <code>Document</code> that will be removed from the <code>Pipeline</code>.
     */
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    /**
     * Checks if the <code>Pipeline</code> contains a certain <code>Document</code>.
     * @param document The <code>Document</code> in question
     * @return True if it contains the <code>Document</code>, else it will return False
     */
    public boolean containsDocument(Document document) {
        return documents.contains(document);
    }

    /**
     * Removes all <code>Document</code>s from the <code>Pipeline</code>.
     */
    public void clearDocuments() {
        documents.clear();
    }

    /**
     * Returns the number of <code>Document</code>s in the <code>Pipeline</code>.
     * @return The number of <code>Document</code>s currently in the <code>Pipeline</code>.
     */
    public int numberOfDocuments() {
        return documents.size();
    }

    /**
     * This method applys all <code>Task</code>s to the <code>Document</code>s.
     */
    public void apply() {
        for(int i = 0; i < documents.size(); i++) {
            Document document = documents.get(i);
            for(int x = 0; x < tasks.size(); x++) {
                Task task = tasks.get(x);
                try {
                    document = task.apply(document);
                } catch (PipelineException e) {
                    getLogger().log(Level.SEVERE, "Error occured while applying tasks to images", e);
                }
            }
        }
    }

    /**
     * Returns the path to the working directory.
     * @return The path to the working directory.
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /***
     * Assigns a new working directory to the <code>Pipeline</code>.
     * @param workingDirectory The path to the new working directory.
     */
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    protected Logger getLogger() {
        return log;
    }

    @Override
    public String toString() {
        return "Processing{" +
                "tasks=" + tasks +
                ", documents=" + documents +
                ", workingDirectory='" + workingDirectory + '\'' +
                '}';
    }
}
