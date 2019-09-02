package com.dowhile.ebrain.brain.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dowhile.ebrain.brain.Normalizer;

public class NormalizerLuceneImpl implements Normalizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NormalizerLuceneImpl.class);
    private final Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
    private final EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer(Version.LUCENE_36);
    private final QueryParser queryParser = new QueryParser(Version.LUCENE_36, "", englishAnalyzer);

    @Override
    public final String clean(final String question) {

        List<String> cleaned = new LinkedList<>();

        LOGGER.trace("Incoming question: " + question);

        // Split the question into words, and lower the case
        for (String token : tokenizer.tokenize(question.toLowerCase().trim())) {

            LOGGER.trace("Token: " + token);

            // For each token, remove anything that isn't a-z or 0-9
            token = token.replaceAll("[^a-z0-9]", "");

            LOGGER.trace("Cleaned token: " + token);

            // Only add to the list if what remains is non-empty
            if (token.length() > 0) {
                LOGGER.trace("Added to list");
                cleaned.add(token);
            }

        }

        LOGGER.trace("Cleaned: " + cleaned.toString());

        return StringUtils.join(cleaned, Normalizer.DELIMITER);

    }

    @Override
    public final String normalize(final String tokens) {

        List<String> normalizedQuestion = new LinkedList<>();

        LOGGER.trace("Normalizing: " + tokens.toString());

        for (String token : StringUtils.split(tokens, Normalizer.DELIMITER)) {
            try {

                LOGGER.trace("Token: " + token);

                // Run through the Lucene parser which does all the "magic"
                token = queryParser.parse(token).toString().trim();

                LOGGER.trace("After parser: " + token);

                // If it is non-empty and doesn't already exist in the list, add it
                if (token.length() > 0) {
                    if (!normalizedQuestion.contains(token)) {
                        LOGGER.trace("Added to list");
                        normalizedQuestion.add(token);
                    }
                }
            } catch (ParseException ex) {
                LOGGER.error("Parse failure while normalizing", ex);
            }
        }

        // sort in alphabetical order
        Collections.sort(normalizedQuestion);

        LOGGER.trace("Sorted: " + normalizedQuestion.toString());

        return StringUtils.join(normalizedQuestion, Normalizer.DELIMITER);

    }
}
