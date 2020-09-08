package com.example.phase2.use_case;

import com.example.phase2.entity.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * CleanText class to tokenize description and calculate tf-idf score and similarity score
 */
public class CleanText {
    //
    private List<String> stopwords;

    /**
     * Constructor for CleanText
     * @param stopwords is a list of string of stop words
     */
    public CleanText (List<String> stopwords) {
        this.stopwords = stopwords;
    }

    /**
     * tokenize the description
     * @param originDescription original description
     * @return a list of tokenized words
     */
    public List<String> tokenizeText(String originDescription) {

        String[] tokenized;
        tokenized = originDescription.replaceAll("[\\s+]", " "). // removes any whitespace character (equal to [\r\n\t\f\v ])
                replaceAll("(['][\\w]+)", ""). // removes any apostrophe and its following word
                replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        List<String> res = new ArrayList<>();
        for(String s: tokenized){
            if(s.length() != 0) {
                res.add(s);
            }
        }
        return res;
    }

    /**
     * Remove all stop words
     * @param tokenizedWords a list of tokenized words
     * @return a list of tokenized words
     */
    public List<String> removeStopWords2(List<String> tokenizedWords) {

        List<String> res = new ArrayList<>();
        for(String word : tokenizedWords) {
            if(!this.stopwords.contains(word)) {
                res.add(word);
            }
        }
        return res;
    }

    /**
     * calculate term frequency
     * @param targetWord a word
     * @param currDoc current document
     * @return Term frequency score
     */
    public double tfCalculator(String targetWord, List<String> currDoc) {
        double count = 0.0;
        for (String word: currDoc) {
            if (word.equals(targetWord)) {
                count++;
            }
        }
        return count / currDoc.size();
    }

    /**
     * Calculate inverse document frequency
     * @param targetWord target word
     * @param tokenizedDescriptionCollection collection of all documents
     * @return inverse document frequency
     */
    public double idfCalculator(String targetWord, List<List<String>> tokenizedDescriptionCollection) {
        // indicator if the List<String> uniqueWordCollection contains the String targetWord
        // indicator can only be 1 or 0
        // 1: indicates List<String> uniqueWordCollection contains the target word
        // 0: indicates List<String> uniqueWordCollection does not contain the target word
        double totalCount = 0.0;
        for (List<String> uniqueWordCollection : tokenizedDescriptionCollection) {
            double indicator = 0.0;
            for (String word : uniqueWordCollection) {
                if (word.equals(targetWord)) {
                    indicator++;
                    break; // break statement is needed since indicator is either 0 or 1
                }
            }
            totalCount += indicator;
        }
        return Math.log10( (1 + tokenizedDescriptionCollection.size()) / (1 + totalCount) ) + 1;
    }

    /**
     * Calculate TF-IDF
     * @param targetWord target word
     * @param currDoc current document
     * @param tokenizedDescriptionCollection collection of all documents
     * @return tf-idf score
     */
    public double tfidfCalculator(String targetWord, List<String> currDoc,
                                  List<List<String>> tokenizedDescriptionCollection) {

        return tfCalculator(targetWord, currDoc) * idfCalculator(targetWord, tokenizedDescriptionCollection);

    }

    /**
     *
     * @param vec a vector of numbers
     * @return the norm of a vector
     */
    public double vecNormCalculator(List<Double> vec) {
        double sum = 0.0;
        for (int i = 0; i < vec.size(); i++) {
            sum += Math.pow(vec.get(i), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Calculate cosine similarity metric
     * @param vec1 one vector of numbers
     * @param vec2 another vector of numbers
     * @return cosine similarity
     */
    public double cosSimCalculator(List<Double> vec1, List<Double> vec2) {
        double dotProduct = 0.0;
        for (int i=0; i < vec1.size(); i++)
            dotProduct += vec1.get(i) * vec2.get(i);

        double vec1Norm = vecNormCalculator(vec1);
        double vec2Norm = vecNormCalculator(vec2);

        return dotProduct / (vec1Norm * vec2Norm);
    }

    /**
     * adding tokenized words to collection of lists and list of unique words
     * @param item an item
     * @param tokenizedCollect collection of lists tokenized description
     * @param bagOfWords a list of unique tokenized words
     */
    public void modifyWordsList(Item item, List<List<String>> tokenizedCollect, List<String> bagOfWords) {

        // handling user item description
        String desc = item.getDescription();
        List<String> tokenizedText = tokenizeText(desc);
        List<String> cleanText = removeStopWords2(tokenizedText);

        // add to collection of tokenized words (a list of list of tokenized words)
        tokenizedCollect.add(cleanText);

        // add userA's clean text to bag of words
        for (String word : cleanText) {
            if (!bagOfWords.contains(word)) {
                bagOfWords.add(word);
            }
        }
    }

}

