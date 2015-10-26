package com.xoom.blog.prattparser;

public class DefaultPrefixExpressionParser implements ExpressionParser {
    @Override
    public ParseNode parseExpression(PrattParser parser, Token token) {
        return new ParseNode(token,null,null);
    }

    @Override
    public ParseNode parseExpressionWithLeft(PrattParser parser, Token token, ParseNode left) {
        throw new IllegalStateException("Invalid call for token " + token.getTokenType());
    }
}
