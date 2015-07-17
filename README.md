# tpp4j
Text processing for java (tpp4j) is a processing pipeline that allows to manipulate and ocr images, clean text files and use NLP methods on them.

Still in development, not ready for production.

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
