package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;

public class Document {
    /*--- Constant ---*/
    private static int space = 0x20, comma = 0x2C, colon = 0x3A, semicolon = 0x3b, hypen = 0x2d, newline = 0x0A;

    private String docId; // Document Identifier
    private StringBuilder DocumentText; //Document Text
    Hashtable<String, Integer> wordOccurrence= new Hashtable<>();
    private int sentenceCounter = 0, phraseCounter = 0;
    private int uniqueWordCounter = 0, totalWordCounter =0, SingleOccurrenceWordCounter = 0;
    /* ------- Task 0 ------- */
    /*  all the basic things  */

    /**
     * Create a new document using a URL
     * @param docId the document identifier
     * @param docURL the URL with the contents of the document; Not Null
     */
    public Document(String docId, @NotNull URL docURL)
    {
        this.docId = docId;
        DocumentText = new StringBuilder();
        try
        {
            Scanner urlScanner = new Scanner(docURL.openStream());
            while(urlScanner.hasNext())
            {
                this.DocumentText.append(urlScanner.nextLine());
            }
        }
        catch(IOException e) {System.out.println("Problem Reading from the URL");}


    }

    /**
     *
     * @param docId the document identifier
     * @param fileName the name of the file with the contents of
     *                 the document
     */
    public Document(String docId, String fileName)
    {
        this.docId = docId;
        DocumentText = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for(String fileLine = reader.readLine(); fileLine != null; fileLine = reader.readLine())
            {
                this.DocumentText.append(fileLine.toString());
            }
        }
        catch(IOException e){System.out.println("Problem Reading from the URL");}
        init();
    }

    /**
     * Obtain the identifier for this document
     * @return the identifier for this document
     */
    public String getDocId()
    {
        return this.docId;
    }

    /**
     *  Break the document into sentence, phrase and words
     *
     */
    private void init()
    {
        String text = this.DocumentText.toString();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        StringBuilder Word = new StringBuilder() ;


        iterator.setText(text);
        int start = iterator.first();
        int tempPhraseCounter = 0;
        boolean isPhraseIndicator = false, isLetterIndicator = false;

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next())
        {
            String sentence = text.substring(start, end);
            this.sentenceCounter++;
            for(int phraseCharIndex = 0; phraseCharIndex < sentence.length(); phraseCharIndex = Word.length() ==0? phraseCharIndex+1 : phraseCharIndex+Word.length())
            {
                Word.setLength(0);
                if(isPhraseIndicator(sentence.charAt(phraseCharIndex))) tempPhraseCounter++;
                if(isLetterIndicator(sentence.charAt(phraseCharIndex)))
                {

                    for(int letterCharIndex = 0; isLetterIndicator(sentence.charAt(phraseCharIndex+letterCharIndex))|| (int)sentence.charAt(phraseCharIndex+letterCharIndex)==hypen; letterCharIndex++)
                    {
                        Word.append(sentence.charAt(phraseCharIndex+letterCharIndex));
                    }
                    if(!Word.isEmpty())
                    {
                        this.totalWordCounter++;
                        if(!this.wordOccurrence.containsKey(Word.toString()) ) this.wordOccurrence.put(Word.toString(),1);
                        else this.wordOccurrence.put(Word.toString(),this.wordOccurrence.get(Word.toString())+1);

                    }

                }
            }

            if(tempPhraseCounter > 1) tempPhraseCounter++;
            tempPhraseCounter = 0;
        }
        this.phraseCounter +=tempPhraseCounter;
    }


    /**
     * Check if the current character is colon, semicolon or comma
     * If ture, then it indicates the presence of phrases
     * @param currentChar: character to be evaluated
     * @return the presence of phrases(colon, semicolon or comma)
     */
    private boolean isPhraseIndicator(char currentChar)
    {
        int charAscii = (int) currentChar;
        return charAscii == colon || charAscii == semicolon || charAscii == comma;
    }

    /**
     * Check if the current character is letter in the alphabet
     * If ture, then it indicates the presence of a letter
     * @param currentChar: character to be evaluated
     * @return the presence of a e letter (colon, semicolon or comma)
     */
    private boolean isLetterIndicator(char currentChar)
    {
        int charAscii = (int) currentChar;
        return (charAscii <= 0x5A && charAscii >= 0x41) || (charAscii >= 0x61 && charAscii <= 0x7A);
    }
    /* ------- Task 1 ------- */

    public double averageWordLength() {
        // TODO: Implement this method
        return 0.0;
    }

    public double uniqueWordRatio() {
        // TODO: Implement this method
        return 0.0;
    }

    public double hapaxLegomanaRatio() {
        // TODO: Implement this method
        return 0.0;
    }

    /* ------- Task 2 ------- */

    /**
     * Obtain the number of sentences in the document
     * @return the number of sentences in the document
     */
    public int numSentences() {
        // TODO: Implement this method
        return 0;
    }

    /**
     * Obtain a specific sentence from the document.
     * Sentences are numbered starting from 1.
     *
     * @param sentence_number the position of the sentence to retrieve,
     * {@code 1 <= sentence_number <= this.getSentenceCount()}
     * @return the sentence indexed by {@code sentence_number}
     */
    public String getSentence(int sentence_number) {
        // TODO: Implement this method
        return null;
    }

    public double averageSentenceLength() {
        // TODO: Implement this method
        return 0.0;
    }

    public double averageSentenceComplexity() {
        // TODO: Implement this method
        return 0.0;
    }

    /* ------- Task 3 ------- */

//    To implement these methods while keeping the class
//    small in terms of number of lines of code,
//    implement the methods fully in sentiments.SentimentAnalysis
//    and call those methods here. Use the getSentence() method
//    implemented in this class when you implement the methods
//    in the SentimentAnalysis class.

//    Further, avoid using the Google Cloud AI multiple times for
//    the same document. Save the results (cache them) for
//    reuse in this class.

//    This approach illustrates how to keep classes small and yet
//    highly functional.

    /**
     * Obtain the sentence with the most positive sentiment in the document
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     * expresses a positive sentiment
     */
    public String getMostPositiveSentence() throws NoSuitableSentenceException {
        // TODO: Implement this method
        return null;
    }

    /**
     * Obtain the sentence with the most negative sentiment in the document
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     * expresses a negative sentiment
     */
    public String getMostNegativeSentence() throws NoSuitableSentenceException {
        // TODO: Implement this method
        return null;
    }

}
