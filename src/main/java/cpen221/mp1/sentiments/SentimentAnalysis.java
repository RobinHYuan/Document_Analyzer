package cpen221.mp1.sentiments;

import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;
import cpen221.mp1.exceptions.NoSuitableSentenceException;

import java.io.IOException;

import static com.google.cloud.language.v1.Sentiment.*;

public class SentimentAnalysis {

    public static String getMostPositiveSentence(cpen221.mp1.Document document)
        throws NoSuitableSentenceException
    {
        StringBuilder maxPositiveSentence = new StringBuilder();
        double mostPositiveScore = 0.3;
        boolean flag = false;

        for(int i = 1; i <= document.numSentences(); i++)
        {
            String text =  document.getSentence(i); // the sentence used for analysis

            try (LanguageServiceClient language = LanguageServiceClient.create())
            {
                Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
                AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
                Sentiment sentiment = response.getDocumentSentiment();
                if (sentiment != null)
                {
                    if(sentiment.getScore()>=mostPositiveScore)
                    {
                        flag = true;
                        maxPositiveSentence.setLength(0);
                        mostPositiveScore = sentiment.getScore();
                        maxPositiveSentence = maxPositiveSentence.append(text);
                    }
                }
            }
            catch (IOException ioe)
            {
                System.out.println(ioe);
                throw new RuntimeException("Unable to communicate with Sentiment Analyzer!");
            }
        }
        if( flag ) return maxPositiveSentence.toString();
        else throw new NoSuitableSentenceException();
    }

    public static String getMostNegativeSentence(cpen221.mp1.Document document)
        throws NoSuitableSentenceException
    {
        StringBuilder maxNegativeSentence = new StringBuilder();
        double mostNegativeScore = -0.3;
        boolean flag = false;

        for(int i = 1; i <= document.numSentences(); i++)
        {
            String text =  document.getSentence(i); // the sentence used for analysis

            try (LanguageServiceClient language = LanguageServiceClient.create())
            {
                Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
                AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
                Sentiment sentiment = response.getDocumentSentiment();
                if (sentiment != null)
                {
                    if(sentiment.getScore()<=mostNegativeScore)
                    {
                        flag = true;
                        maxNegativeSentence.setLength(0);
                        mostNegativeScore = sentiment.getScore();
                        maxNegativeSentence = maxNegativeSentence.append(text);
                    }
                }
            }
            catch (IOException ioe)
            {
                System.out.println(ioe);
                throw new RuntimeException("Unable to communicate with Sentiment Analyzer!");
            }
        }
        if( flag ) return maxNegativeSentence.toString();
        else throw new NoSuitableSentenceException();
    }

}
