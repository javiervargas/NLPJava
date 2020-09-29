import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.util.Properties;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

public class SentimentAnalyzer {

    public TweetWithSentiment findSentiment(String line) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                        longest = partText.length();
                }
            }
        }
        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
            return null;
        }
        TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));
        return tweetWithSentiment;
    }
    private String toCss(int sentiment) {
        switch (sentiment) {
        case 0:
            return "Muy negativo";
        case 1:
            return "Negativo";
        case 2:
            return "Advertencia";
        case 3:
            return "Positivo";
        case 4:
            return "Muy Positivo";
        default:
            return "";
        }
    }
    public TweetWithSentiment getSentiment(String text,String lang){
        Traductor t = new Traductor();
        TweetWithSentiment tweetWithSentiment = null;
        String sentiment = "";
        if(lang.equalsIgnoreCase("en")){
                tweetWithSentiment = this.findSentiment(text.replace("'","''"));
                sentiment = tweetWithSentiment.toString();
        }else{
                if(lang.equalsIgnoreCase("es")){
                        try{
                                tweetWithSentiment = this.findSentiment(t.traducir(text,"es","en").replace("'","''"));
                                     } catch(MalformedURLException f){
                                f.printStackTrace();
                        } catch(URISyntaxException u){
                                u.printStackTrace();
                        }
                }
        }
        return tweetWithSentiment;
    }
    public String getLang(String t){
        LangDetect run = new LangDetect();
        String lang = "";
        try {
                run.init("lib/profiles");
                lang = run.detect(t);
        } catch (LangDetectException e) {
                e.printStackTrace();
        }
        return lang;
    }
    public static void main(String args[]) {
        String lang = "";
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        lang = sentimentAnalyzer.getLang(args[0]);
        System.out.println(sentimentAnalyzer.getSentiment(args[0],lang));
    }
}
                               
