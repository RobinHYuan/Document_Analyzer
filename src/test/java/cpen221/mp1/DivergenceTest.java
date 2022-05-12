package cpen221.mp1;
import cpen221.mp1.similarity.DocumentSimilarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;

public class DivergenceTest
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
        testDocument4 = new Document("Task four test document four", "resources/task4TestDoc4.txt");

    }

    @Test
    public void Test1()
    {
        Assertions.assertEquals(0.786, DocumentSimilarity.jsDivergence(testDocument1,testDocument4), 0.005);
    }


}
