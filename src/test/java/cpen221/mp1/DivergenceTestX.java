package cpen221.mp1;
import cpen221.mp1.similarity.DocumentSimilarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;

public class DivergenceTestX
{

    private static Document testDocument1;
    private static Document testDocument2;
    private static Document testDocument3;
    private static Document testDocument4;
    private static Document testDocument5;
    private static Document DtestDocument1;
    private static Document garbage;


    @BeforeAll
    public static void setupTests() throws MalformedURLException
    {
        testDocument1 = new Document("Task four test document one", "resources/task4TestDoc1.txt");
        testDocument2 = new Document("Task four test document two", "resources/task4TestDoc2.txt");
        testDocument3 = new Document("Task four test document three", "resources/task4TestDoc3.txt");
        testDocument4 = new Document("Task four test document four", "resources/task4TestDoc4.txt");
        testDocument5 = new Document("The Ant and The Cricket", "resources/antcrick.txt");
        DtestDocument1 = new Document("Task four test document one", "resources/task4TestDoc1.txt");
        garbage = new Document( "garbge", "resources/garbageText.txt");

    }

    @Test
    public void antcrick() {
        Assertions.assertEquals(0.0, DocumentSimilarity.documentDivergence(testDocument5,testDocument5), 0.005);
    }

    @Test
    public void jsDivergenceSameDocument() {
        Assertions.assertEquals(0.0, DocumentSimilarity.documentDivergence(testDocument1,testDocument1), 0.005);
    }

    @Test
    public void divergenceSameDocument() {
        Assertions.assertEquals(0.0, DocumentSimilarity.documentDivergence(testDocument1,testDocument1), 0.005);
    }

    @Test
    public void DivergenceSmallDifference() {
        Assertions.assertEquals(6.5562, DocumentSimilarity.documentDivergence(testDocument1,testDocument2), 0.005);
    }

    @Test
    public void DivergenceBigDifference() {
        Assertions.assertEquals(44.4348, DocumentSimilarity.documentDivergence(testDocument1,testDocument3), 0.005);
    }

    @Test
    public void Divergence() {
        Assertions.assertEquals(83.6685, DocumentSimilarity.documentDivergence(testDocument1,testDocument4), 0.005);
    }

    @Test
    public void jsDivergence() {
        Assertions.assertEquals(0.786, DocumentSimilarity.jsDivergence(testDocument1,testDocument4), 0.005);
    }

    @Test
    public void garbageTesting() { //MISSING
        Assertions.assertEquals(64.75000000000003, DocumentSimilarity.documentDivergence(DtestDocument1,garbage), 0.005);
    }
}
