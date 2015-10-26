package com.xoom.blog.prattparser;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PrattParserTest {

    private PrattParser parser;

    @Before
    public void initialize() {
        parser = new PrattParser();
    }

    @Test
    public void evaluateSimpleExpressionTest() {
        Map<String,Integer> variables =  Collections.emptyMap();
        assertEquals(true, parser.eval("2 >= 2", variables));
        assertEquals(false, parser.eval("1 > 2", variables));
    }

    @Test
    public void evaluateSimpleExpressionWithVariablesTest() {

        Map<String,Integer> variables = new HashMap<>();
        variables.put("VAR1", 10);
        variables.put("VAR2", 15);

        assertEquals(true, parser.eval(" VAR1 >= 2", variables));

        assertEquals(false, parser.eval(" VAR1 >= VAR2 ", variables));

        assertEquals(true, parser.eval(" VAR1 < VAR2 AND VAR1+1 > 10", variables));

        assertEquals(false, parser.eval(" VAR1 > VAR2 AND VAR1+1 > 10", variables));

        assertEquals(true, parser.eval(" VAR1 > VAR2 OR VAR1+1 > 10", variables));

        assertEquals(true, parser.eval(" NOT VAR1 > VAR2", variables));

    }

}
