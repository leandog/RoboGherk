package com.leandog.test;

public class FeatureFinder {


    public String getClassName(String featureName) {
        String[] allTheWordsInTheFeatureName = featureName.split(" ");
        return camelCase(allTheWordsInTheFeatureName);
    }

    private String camelCase(String[] allTheWordsInTheFeatureName) {
        StringBuilder classNameBuilder = new StringBuilder(allTheWordsInTheFeatureName.length);
        for(String word : allTheWordsInTheFeatureName) {
               word = (word.charAt(0)+ "").toUpperCase() + word.substring(1, word.length());
               classNameBuilder.append(word);
           }
        return classNameBuilder.toString();
    }
}
