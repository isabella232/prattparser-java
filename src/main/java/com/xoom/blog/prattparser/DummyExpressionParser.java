package com.xoom.blog.prattparser;

public class DummyExpressionParser implements ExpressionParser {

    @Override
    public ParseNode parseExpression(PrattParser parser, Token token) {
        throw new IllegalStateException("Invalid call for token " + token.getTokenType());
    }

    @Override
    public ParseNode parseExpressionWithLeft(PrattParser parser, Token token, ParseNode left) {

        throw new IllegalStateException("Invalid call for token " + token.getTokenType());
    }
}
