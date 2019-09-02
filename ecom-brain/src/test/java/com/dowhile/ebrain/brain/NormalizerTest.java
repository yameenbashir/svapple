package com.dowhile.ebrain.brain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.dowhile.ebrain.brain.Normalizer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class NormalizerTest {

    private Injector injector;
    private Normalizer normalizer;

    @Before
    public final void setUp() {

        BasicConfigurator.configure();
        injector = Guice.createInjector();
        Logger.getRootLogger().setLevel(Level.TRACE);

        normalizer = injector.getInstance(Normalizer.class);

    }

    @Test
    public final void testCleaning() {

        String testQuestion = "What is the meaning of life?";
        List<String> cleaned = Arrays.asList(StringUtils.split(normalizer.clean(testQuestion), Normalizer.DELIMITER));


        List<String> expectedResult = new LinkedList<>();
        expectedResult.add("what");
        expectedResult.add("is");
        expectedResult.add("the");
        expectedResult.add("meaning");
        expectedResult.add("of");
        expectedResult.add("life");

        assert cleaned.equals(expectedResult);

    }

    @Test
    public final void testNormalize() {

        String testQuestion = "What is the meaning of life?";
        List<String> normalized = Arrays.asList(StringUtils.split(normalizer.normalize(normalizer.clean(testQuestion)),
                Normalizer.DELIMITER));

        List<String> expectedResult = new LinkedList<>();
        expectedResult.add("life");
        expectedResult.add("mean");
        expectedResult.add("what");

        assert normalized.equals(expectedResult);

    }
}
