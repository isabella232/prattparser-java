package com.xoom.blog.prattparser;

/**
 * expressionParser is the interface that wraps the token parsing methods
 */
public interface ExpressionParser {
    public ParseNode parseExpression(PrattParser parser, Token token) ;
    public ParseNode parseExpressionWithLeft(PrattParser parser, Token token, ParseNode left) ;
}
