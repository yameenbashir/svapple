package com.dowhile.ebrain.brain;

import com.dowhile.ebrain.brain.impl.NormalizerLuceneImpl;
import com.google.inject.ImplementedBy;

/**
 * Service that provides question / language normalization services.

 */
@ImplementedBy(NormalizerLuceneImpl.class)
public interface Normalizer {

    String DELIMITER = "|";

    /**
     * Takes a question in raw textural form and cleans it to a set of tokens. Each token is roughly standardized, so
     * e.g. punctation is removed, case is lowered, etc.
     *
     * @param question
     *            - the raw text of the question
     * @return - a list of the tokens that comprise the clean question
     */
    String clean(String question);

    /**
     * Normalizes the contents of list of tokens. The intent is to pass the output of something like the "clean" method
     * into this method, and get back a language normalized (stemmed, order removed, duplicates removed, etc.) version
     * of the list.
     *
     * @param cleanedQuestion
     * @return - a list of the tokens in normalized form
     */
    String normalize(String tokens);
}
