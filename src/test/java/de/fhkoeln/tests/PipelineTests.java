package de.fhkoeln.tests;

import de.fhkoeln.Pipeline;
import de.fhkoeln.exceptions.OCRException;
import de.fhkoeln.tasks.OCRTask;
import org.junit.Test;

public class PipelineTests {

    @Test
    public void noExceptionTest() {
        String directory = "src/test/resources/images/input/";
        String document1 = "src/test/resources/images/input/document1/";
        String document2 = "src/test/resources/images/input/document2/";
        String workspace = "src/test/resources/images/output";

/*
        Properties prop = new Properties();
        prop.put("annotators", "tokenize, ssplit, pos, ner, lemma");
        prop.put("tokenize.language", "de");
        prop.put("ner.model", "edu/stanford/nlp/models/ner/german.hgc_175m_600.crf.ser.gz");
        prop.put("ner.applyNumericClassifiers", "false");
        prop.put("ner.useSUTime", "false");
        prop.put("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger");*/

        OCRTask ocr = null;
        try {
            ocr = new OCRTask("deu");
        } catch (OCRException e) {
            e.printStackTrace();
        }

        Pipeline pipeline = new Pipeline(workspace);
        pipeline.addDocument(document1);
        pipeline.addDocument(document2);
        System.out.println("Anzahl der Dokumente: " + pipeline.numberOfDocuments());
        pipeline.addTask(ocr);

        pipeline.apply();
    }
}
