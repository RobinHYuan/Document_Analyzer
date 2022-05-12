package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import cpen221.mp1.sentiments.SentimentAnalysis;
import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;

public class Document
{
    /*--- Constant ---*/
    private static final int space = 0x20, comma = 0x2C, colon = 0x3A, semicolon = 0x3b, hypen = 0x2d, newline = 0x0A, period =0x2e, hashtag = 0x23;

    private String docId; // Document Identifier
    private StringBuilder DocumentText; //Document Text
    Hashtable<String, Integer> wordOccurrence = new Hashtable<>();
    HashSet<String> Key = new HashSet<>();
    private int sentenceCounter = 0, phraseCounter = 0, letterCounter = 0;
    private int uniqueWordCounter = 0, totalWordCounter =0;
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
                this.DocumentText.append(" ");//avoid line break ambiguities
            }
        }
        catch(IOException e) {System.out.println("Problem Reading from the URL");}
        init();
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
                this.DocumentText.append(fileLine);
                this.DocumentText.append(" ");
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
     *  initialize the class inside the constructor
     *  Break the document into sentence, phrase and words
     *  Count the number of sentences, phrases and words
     */

    private void init()
    {

        String text = this.DocumentText.toString();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);

        //Temporary storage for each identified word
        StringBuilder Word = new StringBuilder() ;

        //sentence iterator
        iterator.setText(text);
        int start = iterator.first();
        int tempPhraseCounter = 0;
        boolean isPhraseIndicator = false, isLetterIndicator = false;

        //identify each sentence from the document
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next())
        {
            //store the sentence in a string for future use
            String sentence = text.substring(start, end);
            this.sentenceCounter++;

            // We will now iterate thru the sentence to COUNT the number of phrases and identify each word
            for(int phraseCharIndex = 0; phraseCharIndex < sentence.length(); phraseCharIndex = Word.length() == 0 ? phraseCharIndex + 1 : phraseCharIndex + Word.length())
            {
                //clear temporary variables
                Word.setLength(0);
                tempPhraseCounter = 0;
                //check if any punctuation such as ", or ... or : or ;" exist
                //if exists, then it indicates there are phrases.
                //phrases are NOT stored
                int lastLetterIndex = 0;
                if(isPhraseIndicator(sentence,phraseCharIndex)) tempPhraseCounter++;

                //check for english letters and hashtag(we allow the word to start with a hashtag symbol)
                if(isLetterIndicator(sentence.charAt(phraseCharIndex)) || (int)sentence.charAt(phraseCharIndex)==hashtag )
                {   //check for boundary condition and spaces
                    //if space is found, terminate loop since the word must be ended by a space
                    for(int letterCharIndex = 0; phraseCharIndex+letterCharIndex < sentence.length() && (int)sentence.charAt(phraseCharIndex+letterCharIndex)!=space; letterCharIndex++ )
                    {   //record the very last english letter
                        if(isLetterIndicator(sentence.charAt(phraseCharIndex+letterCharIndex))) lastLetterIndex = phraseCharIndex+ letterCharIndex;
                    }

                    for(int i = phraseCharIndex; i <= lastLetterIndex; i++) Word.append(sentence.charAt(i));

                    if(!Word.isEmpty())// check and see if its empty
                    {    //if not empty
                        this.totalWordCounter++;
                        String tempWord = Word.toString().toLowerCase();
                        //if it doesn't exist in the hashtable, initialized and set the value(number of occurrences ) to 1
                        if(!this.wordOccurrence.containsKey(tempWord) ) this.wordOccurrence.put(tempWord,1);
                        //otherwise increment the value
                        else this.wordOccurrence.put(tempWord,this.wordOccurrence.get(tempWord)+1);
                        this.Key.add(tempWord);
                    }

                }
                if(tempPhraseCounter > 1) tempPhraseCounter++; //the number of phrases is always one more than the number of phrase indicator such as ", or ... or : or ;"
                this.phraseCounter +=tempPhraseCounter;//increment phrase counter
                this.letterCounter += Word.length();//count total number of letter
            }
            if(tempPhraseCounter ==0 ) this.phraseCounter++;//if there is NO phrase punctuations, increment by 1

        }

    }


    /**
     * @param sentence: A string object
     * @param index: index to check for phrase indicator such as ",  or : or ;"  ["..."] doesnt count
     * @return the presence of phrase
     */

    private boolean isPhraseIndicator(String sentence,int index)
    {
        char currentChar = sentence.charAt(index);
        int charAscii = (int) currentChar;
        boolean  BasicPhraseIndicator= charAscii == colon || charAscii == semicolon || charAscii == comma;
        return BasicPhraseIndicator;
        /*
        boolean AdvancedPhraseIndicator = false;
        // check for ellipse within a sentence
        if (charAscii== period && index + 2< sentence.length() && (int)sentence.charAt(index+1)== period && sentence.charAt(index+1) ==sentence.charAt(index+2) )
        {
            //if the ellipse is in the middle of the sentence then it  must be followed by some english letters.
            for(int j = 2; j + index <sentence.length(); j++)
            {
             if( isLetterIndicator(sentence.charAt(j+index)))
                {
                    AdvancedPhraseIndicator= true;
                    break;

                }
            }
        }
        return AdvancedPhraseIndicator||BasicPhraseIndicator;*/
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

    public double averageWordLength() {return (double)this.letterCounter / this.totalWordCounter;}

    public double uniqueWordRatio() {return (double) (this.wordOccurrence.size()) / this.totalWordCounter;}

    public double hapaxLegomanaRatio()
    {
        this.uniqueWordCounter = 0;
        for(String Key: this.Key)
        {
            if(this.wordOccurrence.get(Key) == 1) this.uniqueWordCounter++;
        }
        return (double) this.uniqueWordCounter/this.totalWordCounter;
    }

    /* ------- Task 2 ------- */

    /**
     * Obtain the number of sentences in the document
     * @return the number of sentences in the document
     */
    public int numSentences() {return sentenceCounter;}

    /**
     * Obtain a specific sentence from the document.
     * Sentences are numbered starting from 1.
     * Any unnecessary spaces after sentence-end indicator ". or ? or!" will be deleted
     * @param sentence_number the position of the sentence to retrieve,
     * {@code 1 <= sentence_number <= this.getSentenceCount()}
     * @return the sentence indexed by {@code sentence_number}
     */
    public String getSentence(int sentence_number)
    {
        String text = this.DocumentText.toString();
        String getSentence = new String();

        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(), index = 1; end != BreakIterator.DONE; start = end, end = iterator.next(), index++)
        {
            if(index==sentence_number)
            {
                getSentence = text.substring(start, end);
                break;
            }
        }
        StringBuilder sb = new StringBuilder(getSentence);
        for(int i = 0; i < sb.length(); i++)
        {
            if((int)sb.charAt(sb.length()-1)==space)
            {
                sb.deleteCharAt(sb.length()-1);
            }
        }
        return sb.toString();
    }

    public double averageSentenceLength() {return (double) this.totalWordCounter/(this.sentenceCounter);}

    public double averageSentenceComplexity() {return (double) this.phraseCounter/this.sentenceCounter;}


    public Hashtable<String,Integer> getWordOccurrence()
    {
        Hashtable<String,Integer> copy = new Hashtable<>();
        copy.putAll(wordOccurrence);
        return copy;
    }


    public HashSet getKey()
    {
        HashSet<String> copy = new HashSet<>();
        copy.addAll(this.Key);
        return copy;
    }

    public int getTotalWordCounter() {return totalWordCounter;}
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
    public String getMostPositiveSentence()
            throws NoSuitableSentenceException {return SentimentAnalysis.getMostPositiveSentence(this);}

    /**
     * Obtain the sentence with the most negative sentiment in the document
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     * expresses a negative sentiment
     */
    public String getMostNegativeSentence()
            throws NoSuitableSentenceException {return SentimentAnalysis.getMostNegativeSentence(this);}


    /**
     * @return a 1-D array contains the required info for computing the document divergence
     */

    public double[] getDivMatrix()
    {
        double[] m_iDoc = new double[5];
        m_iDoc[0] = this.averageSentenceLength();
        m_iDoc[1] = this.averageSentenceComplexity();
        m_iDoc[2] = this.averageWordLength();
        m_iDoc[3] = this.uniqueWordRatio();
        m_iDoc[4] = this.hapaxLegomanaRatio();
        return m_iDoc;
    }

}
