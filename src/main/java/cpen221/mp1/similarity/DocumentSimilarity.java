package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class DocumentSimilarity {

    /* DO NOT CHANGE THESE WEIGHTS */
    private static final int WT_AVG_WORD_LENGTH      = 5;
    private static final int WT_UNIQUE_WORD_RATIO    = 15;
    private static final int WT_HAPAX_LEGOMANA_RATIO = 25;
    private static final int WT_AVG_SENTENCE_LENGTH  = 1;
    private static final int WT_AVG_SENTENCE_CPLXTY  = 4;
    private static final int WT_JS_DIVERGENCE        = 50;


    private static final int[] weight= {WT_AVG_SENTENCE_LENGTH,WT_AVG_SENTENCE_CPLXTY, WT_AVG_WORD_LENGTH,
                                WT_UNIQUE_WORD_RATIO, WT_HAPAX_LEGOMANA_RATIO};
    /* ---- END OF WEIGHTS ------ */

    /* ------- Task 4 ------- */

    /**
     * Compute the Jensen-Shannon Divergence between the given documents
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Jensen-Shannon Divergence between the given documents
     */
    public static double jsDivergence(Document doc1, Document doc2)
    {
        double wi, pi, qi, mi, sum = 0;
        Hashtable<String,Integer> doc1_WordOccurrence = doc1.getWordOccurrence();
        Hashtable<String,Integer> doc2_WordOccurrence = doc2.getWordOccurrence();

        Set<String> doc1_KeyWord = doc1_WordOccurrence.keySet();
        Set<String> doc2_KeyWord = doc2_WordOccurrence.keySet();
        Set<String> KeyTotal = new HashSet<>();

        KeyTotal.addAll(doc1_KeyWord);
        KeyTotal.addAll(doc2_KeyWord);

        int doc1_totalWords = doc1.getTotalWordCounter();
        int doc2_totalWords = doc2.getTotalWordCounter();
        //summation of all words in doc1

        for(String Key: KeyTotal)
        {
            int f1 = doc1_WordOccurrence.get(Key) == null ? 0:doc1_WordOccurrence.get(Key);
            int f2 = doc2_WordOccurrence.get(Key) == null ? 0:doc2_WordOccurrence.get(Key);

            pi = (double) f1/doc1_totalWords;
            qi = (double) f2/doc2_totalWords;
            mi = (pi + qi)/2;

            double partA = (pi == 0)? 0 : pi*(Math.log(pi/mi) / Math.log(2));
            double partB = (qi == 0)? 0 : qi*(Math.log(qi/mi) / Math.log(2));
            sum += (partA + partB);
        }
        return sum/2.0;
    }

    /**
     * Compute the Document Divergence between the given documents
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Document Divergence between the given documents
     */
    public static double documentDivergence(Document doc1, Document doc2)
    {
        int matrixSize = 6;
        double sum = 0;
        double[] m_iDoc1 = doc1.getDivMatrix();
        double[] m_iDoc2 = doc2.getDivMatrix();
        for(int i = 0; i< matrixSize; i++)
        {
           sum += weight[i]*Math.abs(m_iDoc1[i]-m_iDoc2[i]);
        }
        return sum + jsDivergence(doc1, doc2)* WT_JS_DIVERGENCE;
    }
}
