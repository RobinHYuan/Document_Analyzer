
## Document Analyzer

The document analyzer is created for analyzing text documents based on various characteristics of each of the document. 
It can be used for text extraction, plagiarism checking and sentiment analysis using third-party APIs.

### Part I: Text analysis 
A ```document``` class is created for storing the text and helping perform certain operations and analyses. 
In this task, I performed the following operations after retrieving the text from a URL or a local file path:
- Split the document into its consistent sentences to determine:
    - The average number of words[^1] in a sentence, m<sub>1</sub>
    - The average number of phrases per sentence, m<sub>2</sub>
- Break the document down further into phrases and words to compute:
  - The average word length, m<sub>3</sub>
  - The number of unique words to the total number of words, m<sub>4</sub>
  - The number of words occurring exactly once to the total number of words, m<sub>5</sub>

[^1]:  A word is a non-empty token that is not completely made up of punctuation. 
If a token begins or ends with punctuation then a word can be obtained by removing the starting and trailing punctuation.
Specifically, the start of word should not contain any of ! " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | }.
The end of a word should not include any of ! # " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~.