package com.xoom.blog.prattparser;


public class TokenParser {

    private Token token;
    private ExpressionParser expressionParser;

    public TokenParser(ExpressionParser expressionParser, Token token) {
        this.expressionParser = expressionParser;
        this.token = token;
    }

    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
