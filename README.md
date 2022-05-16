
## Document Analyzer

The document analyzer is created for analyzing text documents based on various characteristics of each of the document. 
It can be used for text extraction, plagiarism checking and sentiment analysis using third-party APIs.

### Part I: Text analysis 
A ```document``` class is created for storing the text and helping perform certain operations and analyses. 
In this task, I performed the following operations after retrieving the text from a URL or a local file path:
- Split the document into its consistent sentences to determine:
    - The average number of words in a sentence, m<sub>1</sub>
    - The average number of phrases per sentence, m<sub>2</sub>
- Break the document down further into phrases and words to compute:
  - The average word length, m<sub>3</sub>
  - The number of unique words to the total number of words, m<sub>4</sub>
  - The number of words occurring exactly once to the total number of words, m<sub>5</sub>

