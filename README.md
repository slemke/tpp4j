# tpp4j
Text processing for java (tpp4j) is a processing pipeline that allows to manipulate and ocr images, clean text files and use NLP methods such as tokenization or part-of-speech tagging provided by the Stanford Core NLP Tools.

Tpp4j is an API that was created during a student project at the cologne unversity of applied sciences in collaboration with the [ZBMED - Leibniz-Informationszentrum Lebenswissenschaften](http://www.zbmed.de/). Tpp4j uses the MIT License.

The pipeline is optimized for german, but should also work for languages. To use different languages you need to modify the abbreviations.csv,  cleaning.csv and stopwords.txt as they contain language specific data. You'll also need to load a different language model for the NLP Tools. To do that you just need to change the maven config file to the appropriate language.

Below you can find a simple example:

```java
String document2 = "src/test/resources/images/input/document2/";
String document1 = "src/test/resources/images/input/document1/";
String workspace = "src/test/resources/images/tasks/all/";

Properties prop = new Properties();
prop.put("annotators", "tokenize, ssplit, pos, ner, lemma");
prop.put("tokenize.language", "de");
prop.put("ner.model", "edu/stanford/nlp/models/ner/german.hgc_175m_600.crf.ser.gz");
prop.put("ner.applyNumericClassifiers", "false");
prop.put("ner.useSUTime", "false");
prop.put("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger");

ImageProcessingTask imp = new ImageProcessingTask(1.0, 10, 0.0, true, true, false);
OCRTask task = new OCRTask("deu");
TextCleaningTask cleaningTask = new TextCleaningTask("");
NLPTask nlpTask = new NLPTask(prop);

Pipeline pipeline = new Pipeline(workspace);
pipeline.addDocument(document1);
pipeline.addDocument(document2);

System.out.println("Anzahl der Dokumente: " + pipeline.numberOfDocuments());
pipeline.addTask(imp);
pipeline.addTask(task);
pipeline.addTask(cleaningTask);
pipeline.addTask(nlpTask);

pipeline.apply();
```

Currently only JPEG, TIF and plain text files are supported. The properties object contains the information for the Stanford Core NLP Tools you can find [here](http://nlp.stanford.edu/software/corenlp.shtml). The pipeline provides four tasks you can use to process information, but you can also implement your own by extending the `Task` parent class:

- ImageProcessingTask: provides methods to improvide the quality for the OCR process. You can make the images grayscale, binary or change brightness and contrast.
- OCRTask: allows to OCR jpeg and tifs with Tesseract. You need to install tesseract manually.
- TextCleaningTask: allows for simple text cleaning to remove OCR fragments or other problems. You can use the default patterns or provide your own cvs file using the format "pattern;replacement".
- NLPTask: The NLP Task analyzes the text documents you provided (or the ocr documents) useing the Stanford Core NLP Tool. Eventhough not implementing every method the tools provide you can extract sentences, remove stop words, tokenize, assign POS Tags and NER Tags.

```java
public final class NewTask extends Task {

    @Override
    public Document apply(Document document) throws PipelineException {
      // implement your code here
    }
}
```

You can generate javadocs for an extended documentation of every method.
