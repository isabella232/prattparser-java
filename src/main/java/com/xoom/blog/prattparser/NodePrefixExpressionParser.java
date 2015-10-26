package com.xoom.blog.prattparser;

public class NodePrefixExpressionParser implements  ExpressionParser {
    @Override
    public ParseNode parseExpression(PrattParser parser, Token token) {
        ParseNode left = parser.parse(token.getTokenType().getPrecedenceLevel());
        return new ParseNode(token,left,null);
    }

    @Override
    public ParseNode parseExpressionWithLeft(PrattParser parser, Token token, ParseNode left) {
        throw new IllegalStateException("Invalid call for token " + token.getTokenType());
    }
}
