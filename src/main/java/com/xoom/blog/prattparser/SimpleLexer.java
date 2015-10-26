package com.xoom.blog.prattparser;

import java.io.*;

public class SimpleLexer {

    private PushbackReader reader;
    public SimpleLexer(String input) {
        reader = new PushbackReader(new StringReader(input));
    }

    public Token next() {
        Token token = null;
        try {
            int c = reader.read();  //when end of stream is reached, 65535 is returned
            if (Character.isSpaceChar((char) c)) {
                token = skipSpace();
            } else if (c == '+') {
                token = new Token(TokenType.PLUS,TokenType.PLUS.name());
            } else if (c == '-') {
                token = new Token(TokenType.MINUS, TokenType.MINUS.name());
            } else if (c == '*') {
                token = new Token(TokenType.MULTIPLY,TokenType.MULTIPLY.name());
            } else if (c == '(') {
                token = new Token(TokenType.LEFTPAREN,TokenType.LEFTPAREN.name());
            } else if (c == ')') {
                token = new Token(TokenType.RIGHTPAREN,TokenType.RIGHTPAREN.name());
            } else if (c == '>') {
                if (isNextMatch('=')) {
                    token = new Token(TokenType.GREATEREQUAL, TokenType.GREATEREQUAL.name());
                } else {
                    token = new Token(TokenType.GREATER, TokenType.GREATER.name());
                }
            } else if (c == '<') {
                if (isNextMatch('=')) {
                    token = new Token(TokenType.LESSEREQUAL, TokenType.LESSEREQUAL.name());
                } else {
                    token = new Token(TokenType.LESSER, TokenType.LESSER.name());
                }
            } else if (c == '=') {
                if (isNextMatch('=')) {
                    token = new Token(TokenType.EQUAL, TokenType.EQUAL.name());
                } else {
                    throw new IllegalStateException("Expected = after = , but found " + (char)reader.read());
                }
            } else if (c == '!') {
                if (isNextMatch('=')) {
                    token = new Token(TokenType.EQUAL, TokenType.EQUAL.name());
                } else {
                    throw new IllegalStateException("Expected = after !, but found " + (char)reader.read());
                }
            } else if (Character.isLetter((char) c)) {
                token = extractIndenifier((char) c);
            } else if (Character.isDigit((char) c)) {
                token = extractNumber((char) c);
            } else if ((byte)c == -1) {
                token = new Token(TokenType.END,TokenType.END.name());
            } else {
                throw new IllegalStateException("Unhandled char: " + (char)c + " with int value: " + c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Stream reading error", e);
        }
        return token;
    }

    private boolean isNextMatch(char c) throws IOException {
        int n = reader.read();
        if ((char)n == '=') {
            return true;
        } else {
            reader.unread(n);
            return false;
        }
    }

    private Token extractIndenifier(char c) throws IOException  {
        StringBuilder builder = new StringBuilder();
        builder.append(c) ;
        int n;
        while (true) {
            n = reader.read();
            if (Character.isLetterOrDigit((char)n) || (char)n == '_')  {
                builder.append((char) n);
            } else {
                break;
            }
        }
        reader.unread(n);
        String s = builder.toString();

        switch(s) {
            case "AND":
                return new Token(TokenType.AND, s);
            case "OR":
                return new Token(TokenType.OR, s);
            case "NOT":
                return new Token(TokenType.NOT, s);
            default:
                return new Token(TokenType.IDENTIFIER, s);
        }
    }

    private Token skipSpace() throws IOException {
        int n;
        while (true) {
           n = reader.read();
            if (!Character.isSpaceChar((char)n)) {
               break;
            }
        }
        reader.unread(n);
        return next();
    }

    private Token extractNumber(char c) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(c) ;
        int n;
        while (true) {
            n = reader.read();
            if (Character.isDigit((char)n)) {
                builder.append((char) n);
            } else {
                break;
            }
        }
        reader.unread(n);
        return new Token(TokenType.NUMBER,builder.toString());
    }


    public static void main(String[] args) {
        SimpleLexer lexer = new SimpleLexer("12 == !=  * >  8 >= (13+67)-78 JK_Mn1") ;

        while(true) {
            Token t = lexer.next();
            if (t.getTokenType() == TokenType.END) {
                break;
            }
            System.out.println(" Token is " + t);
        }
    }
}