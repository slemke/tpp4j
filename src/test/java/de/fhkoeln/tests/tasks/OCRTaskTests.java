package de.fhkoeln.tests.tasks;

import de.fhkoeln.Pipeline;
import de.fhkoeln.exceptions.OCRException;
import de.fhkoeln.io.TextReader;
import de.fhkoeln.tasks.OCRTask;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OCRTaskTests {

    @Test
    public void ocrTaskTests() throws OCRException, IOException {
        String document = "src/test/resources/images/input/document2/document2.jpg";
        String workspace = "src/test/resources/images/tasks/ocr/";
        OCRTask task = new OCRTask("deu");
        Pipeline pipeline = new Pipeline(workspace);
        pipeline.addDocument(document);
        System.out.println("Anzahl der Dokumente: " + pipeline.numberOfDocuments());
        pipeline.addTask(task);

        pipeline.apply();


        File data = new File("src/test/resources/images/tasks/ocr/document2/ocr/document2.txt");
        TextReader reader = new TextReader();
        String read = reader.read(data);
        assertEquals(true, data.exists());
        assertThat(read, not(""));
    }

}
