package cpen221.mp1;

import cpen221.mp1.sentiments.SentimentAnalysis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import cpen221.mp1.exceptions.NoSuitableSentenceException;


public class GoogleSentimentUnitTest
{
    private static Document testDocument1;

    @BeforeAll
    public static void setupTests()
    {
        testDocument1 = new Document("The Ant and The Cricket", "resources/antcrickORG.txt");
    }

    @Test
    public void testSentimentsPos()
    {
        try {Assertions.assertEquals("\"Well, try dancing now!\"", SentimentAnalysis.getMostPositiveSentence(testDocument1));}
        catch (NoSuitableSentenceException e) {throw new RuntimeException(e);}
    }

    @Test
    public void testSentimentsNeg()
    {
        try {Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.", SentimentAnalysis.getMostNegativeSentence(testDocument1));}
        catch(NoSuitableSentenceException e) {throw new RuntimeException(e);}
    }


}
