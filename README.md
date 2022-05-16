
## Document Analyzer

The document analyzer is created for analyzing text documents based on various characteristics of each of the document. 
It can be used for text extraction, plagiarism checking and sentiment analysis using third-party APIs.

### Part I: Text analysis 
A ```document``` class is created for storing the text and helping perform certain operations and analyses. 
In this task, I performed the following operations after retrieving the text from a URL or a local file path:
- Split the document into its consistent sentences[^1] to determine:
    - The average number of words[^2] in a sentence, m<sub>1</sub>
    - The average number of phrases[^3] per sentence, m<sub>2</sub>
- Break the document down further into phrases and words to compute:
  - The average word length, m<sub>3</sub>
  - The number of unique words to the total number of words, m<sub>4</sub>
  - The number of words occurring exactly once to the total number of words, m<sub>5</sub>


### Part II: Sentiment Analysis
In this part, Google Cloud Natural Language client library is used to detect the sentiment[^4] of each sentence 
in the given document. Using Google's Natural Language AI system, we can obtain a sentiment score and a sentiment magnitude. 
A sentence is positive if the sentiment score is ≥ 0.3. A sentence is negative is the sentiment score is ≤ -0.3.

<p align="center"><img src="resources/API.png" width="90%" ></p>  

### Part III: Document Similarity Analysis
We now have all the required metrics to group different documents. The similarity of any two documents are determined by their document divergences and union-find.
In order to determine their document divergences, we will first compute their `Jensen-Shannon Divergences`: </br>

</br>![equation](https://latex.codecogs.com/svg.image?%5Cinline%20%5CLARGE%20JSD(P%7C%7CQ)%20=%20%5Cfrac%7B1%7D%7B2%7D%5Csum_%7Bi%20=%201%7D%5E%7Bn%7D(p_ilog_2%20%5Cfrac%7Bp_i%7D%7Bm_i%7D%20&plus;1_ilog_2%20%5Cfrac%7B1_i%7D%7Bm_i%7D%20))

In our context, we defined *p<sub>i</sub>* as the probability of word *ω<sub>i</sub>* appearing in document **P** and  *q<sub>i</sub>* as the probability of word *ω<sub>i</sub>* appearing in document **Q**.
The summation is over all words that appear in the two documents together. If *ω<sub>i</sub>* appears in **P** and not in **Q** then *q<sub>i</sub>* = 0. Further,
by definition, ![equation](https://latex.codecogs.com/svg.image?%5Ctiny%20plog_2(p/m)=%200%20%5Ctextup%7B%20when%20%7D%20p%20=0) and ![equation](https://latex.codecogs.com/svg.image?\tiny&space;m_i&space;=(p_i&plus;q_i)/{2}) .
The Jensen-Shannon Divergence uses the frequency with which words appear to determine if two documents are divergent or not.

We will now calculate the group divergence of any two given documents using the following definition:
![equation](https://latex.codecogs.com/svg.image?%5Cinline%20%5CDelta_%7B1,2%7D%20=%20(%5Csum_%7Bi%20=%201%7D%5E%7B5%7D%20w_i%5Cdelta_i)&plus;w_%7Bjs%7D%5Cdelta_%7Bjs%7D%20%5Ctextup%7B%20where%20%7D%5Cdelta_i%20=%20%7C%7Cm_%7Bi,1%7D%20-%20m_%7Bi,2%7D%7C%7C,m_%7Bi,j%7D%20%5Ctextup%7B%20is%20the%20value%20of%20%7D%20m_i%20%5Ctextup%7B%20for%20document%7D%20D_j%5Ctextup%7B%20and%20%7D%20w_i%20%5Ctextup%7B%20and%20%7D%20w_%7Bjs%7D%20%5Ctextup%7B%20are%20given%20weights.%7D)
</br>
Note that ***𝛿<sub>js</sub>*** is the Jensen-Shannon divergence. The larger the divergence, the more dissimilar the two documents are.
</br>


[^1]: We will consider a sentence to be a sequence of characters that is terminated by  the characters ```! ? .``` or 
EOF excludes whitespace on either end and is not empty.

[^2]: A word is a non-empty token that is not completely made up of punctuation. 
If a token begins or ends with punctuation then a word can be obtained by removing the starting and trailing punctuation.
Specifically, the start of word should not contain any of ``` ``! " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | }.```
The end of a word should not include any of ``` ``! # " $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~.``` 
Hyphenated words are considered to be one word. Words are allowed to start with hashtags `#`.

[^3]: A phrase is a non-empty (empty = empty string or whitespace only) part of a sentence that 
is separated from another phrase by commas, colons and semi-colons.

[^4]:It is important to note that the Natural Language API indicates differences between positive and negative emotion in a document,
but does not identify specific positive and negative emotions. 
For example, "angry" and "sad" are both considered negative emotions. 
However, when the Natural Language API analyzes text that is considered "angry", or text that is considered "sad", 
the response only indicates that the sentiment in the text is negative, not "sad" or "angry".
