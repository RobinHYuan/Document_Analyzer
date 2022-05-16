package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.util.*;

public class GroupingDocuments {

    /* ------- Task 5 ------- */

    /**
     * Group documents by similarity
     * @param allDocuments the set of all documents to be considered,
     *                     is not null
     * @param numberOfGroups the number of document groups to be generated
     * @return groups of documents, where each group (set) contains similar
     * documents following this rule: if D_i is in P_x, and P_x contains at
     * least one other document, then P_x contains some other D_j such that
     * the divergence between D_i and D_j is smaller than (or at most equal
     * to) the divergence between D_i and any document that is not in P_x.
     */
    public static Set<Set<Document>> groupBySimilarity(Set<Document> allDocuments, int numberOfGroups)
    {
        Document[] docArray = allDocuments.toArray(new Document[0]);
        double [][] divMatrix = new double[allDocuments.size()][allDocuments.size()];
        List<Set<Document>> group = new ArrayList<>();

        double minDiv = 1000;
        int docGrouped = 0;

        for(int i = 0; i < allDocuments.size(); i++)
        {
            for(int j = 0; j < allDocuments.size(); j++)
            {
               divMatrix[i][j] = DocumentSimilarity.documentDivergence(docArray[i],docArray[j]);

            }
            docArray[i].setNum = -1;
        }

        for(int i = 0, setCounter = 0; i < allDocuments.size() && docGrouped < allDocuments.size(); i++)
        {
            minDiv = 1000;
            int minIndex = -1;
            for(int j = 0; j < allDocuments.size(); j++)
            {

               if((docArray[i].setNum == -1 || docArray[j].setNum == -1) && (divMatrix[i][j] < minDiv && divMatrix[i][j] != 0))
               {
                   boolean min = true;
                   for(int k = 0; k < allDocuments.size(); k++)
                   {
                       if(divMatrix[i][j] > divMatrix[j][k] && divMatrix[j][k] != 0 && docArray[k].setNum == -1)
                       {
                           min =false;
                           System.out.println("divMatrix[i][j] is " + divMatrix[i][j] + "  ==> i: " + i + ", j: "+ j);
                           System.out.println("divMatrix[j][k] is " + divMatrix[j][k] + "  ==> j: " + j + ", k: "+ j);
                       }
                   }
                   if(min)
                   {
                       minDiv = divMatrix[i][j];
                       minIndex = j;
                       System.out.println("divMatrix[i][j] MIN is " + divMatrix[i][j] );
                   }
               }
            }

            if(allDocuments.size() - docGrouped == numberOfGroups - group.size() || (allDocuments.size() - docGrouped == numberOfGroups  && group.isEmpty()))
            {
                for(int index = 0; index < allDocuments.size(); index++)
                {
                    if(docArray[index].setNum == -1)
                    {
                        Set<Document> temp = new HashSet<>();
                        temp.add(docArray[index]);
                        docArray[index].setNum = setCounter;
                        group.add(temp);

                    }
                }
                break;
            }
            else if(minIndex != -1)
            {
                if(docArray[i].setNum == -1 && docArray[minIndex].setNum == -1)
                {
                    Set<Document> temp = new HashSet<>();
                    temp.add(docArray[i]);
                    temp.add(docArray[minIndex]);
                    group.add(temp);
                    docArray[i].setNum = setCounter;
                    docArray[minIndex].setNum = setCounter;
                    setCounter++;
                    docGrouped += 2;

                }
                else if (docArray[i].setNum == -1)
                {
                    group.get(docArray[minIndex].setNum).add(docArray[i]);
                    docArray[i].setNum = docArray[minIndex].setNum;
                    docGrouped++;

                }
                else
                {
                    group.get(docArray[i].setNum).add(docArray[minIndex]);
                    docArray[minIndex].setNum = docArray[i].setNum;
                    docGrouped++;

                }
            }

        }
        Set<Set<Document>> result = new HashSet<>();
        for(int i = 0; i< group.size(); i++)
        {
            result.add(group.get(i));
        }
        return result;
    }
}
