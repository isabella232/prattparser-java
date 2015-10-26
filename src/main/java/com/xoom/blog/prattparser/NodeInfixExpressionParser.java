package com.xoom.blog.prattparser;

public class NodeInfixExpressionParser implements ExpressionParser {

    @Override
    public ParseNode parseExpression(PrattParser parser, Token token) {
        throw new IllegalStateException("Invalid call for token " + token.getTokenType());
    }

    @Override
    public ParseNode parseExpressionWithLeft(PrattParser parser, Token token, ParseNode left) {
        ParseNode  right = parser.parse(token.getTokenType().getPrecedenceLevel());
        return new ParseNode(token,left,right);
    }
}
