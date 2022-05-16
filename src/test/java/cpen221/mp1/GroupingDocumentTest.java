package cpen221.mp1;
import org.junit.jupiter.api.BeforeAll;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.net.MalformedURLException;
import static cpen221.mp1.similarity.GroupingDocuments.groupBySimilarity;

public class GroupingDocumentTest
{
        private static Document testDocument1;
        private static Document testDocument2;
        private static Document testDocument3;
        private static Document testDocument4;
        private Set<Document> testSet1;
        private Set<Document> testSet2;
        private Set<Document> testSet3,testSet31;
        private Set<Document> testSet4;
        private Set<Set<Document>> bigSet;


        @BeforeAll
        public static void setupTests() throws MalformedURLException {
            testDocument1 = new Document("Task four test document one", "resources/task4TestDoc1.txt");
            testDocument2 = new Document("Task four test document two", "resources/task4TestDoc2.txt");
            testDocument3 = new Document("Task four test document three", "resources/task4TestDoc3.txt");
            testDocument4 = new Document("Task four test document four", "resources/task4TestDoc4.txt");

        }

    @Test
    public void groupingFourDocumentsInThree() {
        testSet1 = new HashSet<>();
        testSet1.add(testDocument1);
        testSet1.add(testDocument2);

        testSet2 = new HashSet<>();
        testSet2.add(testDocument3);

        testSet3 = new HashSet<>();
        testSet3.add(testDocument4);


        bigSet = new HashSet<>();
        bigSet.add(testSet1);
        bigSet.add(testSet2);
        bigSet.add(testSet3);


        testSet4 = new HashSet<>();
        testSet4.add(testDocument1);
        testSet4.add(testDocument2);
        testSet4.add(testDocument3);
        testSet4.add(testDocument4);

        Assertions.assertEquals(bigSet, groupBySimilarity(testSet4, 3));
    }

    @Test
    public void documentInTheirOwnGroup() {
        testSet1 = new HashSet<>();
        testSet2 = new HashSet<>();
        testSet1.add(testDocument1);
        testSet2.add(testDocument2);

        testSet3 = new HashSet<>();
        testSet3.add(testDocument1);
        testSet3.add(testDocument2);

        Set<Set<Document>> bigSet = new HashSet<>();
        bigSet.add(testSet1);
        bigSet.add(testSet2);
        Assertions.assertEquals(bigSet, groupBySimilarity(testSet3, 2));

    }


    @Test
    public void allInOne() {
        testSet1 = new HashSet<>();
        testSet1.add(testDocument1);
        testSet1.add(testDocument2);
        testSet1.add(testDocument3);

        testSet31 = new HashSet<>();
        testSet31.add(testDocument1);
        testSet31.add(testDocument2);
        testSet31.add(testDocument3);


        Set<Set<Document>> bigSet = new HashSet<>();
        bigSet.add(testSet1);
        Assertions.assertEquals(bigSet, groupBySimilarity(testSet31, 1));
    }

    @Test
    public void randomTesting() {
        testSet1 = new HashSet<>();
        testSet1.add(testDocument4);

        testSet2 = new HashSet<>();
        testSet2.add(testDocument1);
        testSet2.add(testDocument2);
        testSet2.add(testDocument3);



        testSet3 = new HashSet<>();
        testSet3.add(testDocument1);
        testSet3.add(testDocument2);
        testSet3.add(testDocument3);
        testSet3.add(testDocument4);


        Set<Set<Document>> bigSet = new HashSet<>();
        bigSet.add(testSet1);
        bigSet.add(testSet2);
        Assertions.assertEquals(bigSet, groupBySimilarity(testSet3, 2));
    }
}
