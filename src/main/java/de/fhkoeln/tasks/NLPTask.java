package de.fhkoeln.tasks;

import de.fhkoeln.datatypes.Document;
import de.fhkoeln.datatypes.Text;
import de.fhkoeln.datatypes.Token;
import de.fhkoeln.exceptions.OCRException;
import de.fhkoeln.exceptions.PipelineException;
import de.fhkoeln.filter.StopWordFilter;
import de.fhkoeln.io.TextWriter;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * This class defines the NLP task using the Stanford NLP Core Tools.
 * @author Sascha Lemke
 */
public final class NLPTask extends Task {

    private Properties properties;
    private static StanfordCoreNLP pipeline;

    /**
     * Creates a new NLP Task for the <code>Pipeline</code> using the <code>Properties</code> file for the Stanford Core NLP Tools.
     * @param properties The properties for the Stanford Core NLP Files. For information see here: http://nlp.stanford.edu/software/corenlp.shtml
     */
    public NLPTask(Properties properties) {
        this.properties = properties;
        pipeline = new StanfordCoreNLP(properties);
    }


    @Override
    public Document apply(Document document) throws PipelineException {
        if(!(document instanceof Text)) {
            getLogger().log(Level.WARNING, "Document for NLPTask is not a text document. Skipping!");
            return document;
        }

        Text text = (Text) document;
        ArrayList<Token> tokens = new ArrayList<Token>();
        ArrayList<String> sentenceList = new ArrayList<String>();

        StringBuilder tokenizedSb = new StringBuilder();
        StringBuilder noStopwordsSb = new StringBuilder();
        StringBuilder allSb = new StringBuilder();

        File directory = new File(getWorkingDirectory() + File.separator + text.getFileName());
        if(!directory.exists() && !directory.mkdir()) {
            getLogger().log(Level.SEVERE, "Couldn't create " + directory.getPath() +  "!");
            throw new OCRException("Couldn't create " + directory.getPath() +  "!");
        }

        File tokenizedOutput = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "tokenized.txt");
        File noStopwordsOutput = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "tokenized.nostopwords.txt");
        File all = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "data.txt");
        File sentenceFile = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "sentence.txt");
        File xmlOutput = new File(getWorkingDirectory() + File.separator + text.getFileName() + File.separator + "document.xml");

        Annotation annotation = new Annotation(text.getText());
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence : sentences) {
            String line = sentence.toString();
            sentenceList.add(line);
            for(CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                Token listToken = new Token(token.get(CoreAnnotations.TextAnnotation.class));
                listToken.setPosTag(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));
                listToken.setNerLabel(token.get(CoreAnnotations.NamedEntityTagAnnotation.class));
                tokens.add(listToken);
            }
        }

        for(Token token : tokens) {
            tokenizedSb.append(token.getToken());
            tokenizedSb.append(System.lineSeparator());
        }
        ArrayList<Token> noStopwords = new ArrayList<Token>();
        try {
            StopWordFilter filter = new StopWordFilter("stopwords.txt");
            noStopwords = filter.removeStopwords(tokens);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Couldn't read stopword list", e);
        }

        for(Token token: noStopwords) {
            noStopwordsSb.append(token.getToken());
            noStopwordsSb.append(System.lineSeparator());
            allSb.append(token.getToken());
            allSb.append(" ");
            allSb.append(token.getPosTag());
            allSb.append(" ");
            allSb.append(token.getNerLabel());
            allSb.append(System.lineSeparator());
        }
        StringBuilder sentenceSb = new StringBuilder();

        for(String sentence : sentenceList) {
            sentence = sentence.replaceAll("\\r?\\n", " ");
            sentenceSb.append(sentence);
            sentenceSb.append(System.lineSeparator());
        }

        text.setTokenizedText(tokens);
        text.setStopWordFreeText(noStopwords);

        try {
            TextWriter writer = new TextWriter();
            writer.write(tokenizedSb.toString(), tokenizedOutput.getPath());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Couldn't write file: ", e);
        }

        try {
            TextWriter writer = new TextWriter();
            writer.write(noStopwordsSb.toString(), noStopwordsOutput.getPath());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Couldn't write file: ", e);
        }

        try {
            TextWriter writer = new TextWriter();
            writer.write(allSb.toString(), all.getPath());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Couldn't write file: ", e);
        }

        try {
            TextWriter writer = new TextWriter();
            writer.write(sentenceSb.toString(), sentenceFile.getPath());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Couldn't write file: ", e);
        }
        return text;
    }
}
