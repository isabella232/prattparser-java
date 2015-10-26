package com.xoom.blog.prattparser;

/**
 * All token types that can be used in the input expressions
 */
public enum TokenType {
    LEFTPAREN(1),
    RIGHTPAREN(1),
    PLUS(40),
    MINUS(40),
    MULTIPLY(50),
    GREATER(30),
    GREATEREQUAL(30),
    LESSER(30),
    LESSEREQUAL(30),
    EQUAL(30),
    NOTEQUAL(30),
    AND(25),
    OR(25),
    NOT(0),
    NUMBER(0),
    IDENTIFIER(0),
    END(0),
    ;

    // The token's precedence or binding power
    private int precedenceLevel = 0;

    TokenType(int precedenceLevel) {
        this.precedenceLevel = precedenceLevel;
    }

    public int getPrecedenceLevel() {
        return this.precedenceLevel;
    }

}
