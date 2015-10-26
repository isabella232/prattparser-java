package com.xoom.blog.prattparser;

public class LeftparenPrefixExpressionParser implements  ExpressionParser {
    @Override
    public ParseNode parseExpression(PrattParser parser, Token token) {
        ParseNode left =  parser.parse(token.getTokenType().getPrecedenceLevel()) ;
        parser.nextMatch(TokenType.RIGHTPAREN);
        return left;
    }

    @Override
    public ParseNode parseExpressionWithLeft(PrattParser parser, Token token, ParseNode left) {
        throw new IllegalStateException("Invalid call for token " + token.getTokenType());
    }
}
