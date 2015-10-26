package com.xoom.blog.prattparser;

public class ParseNode {
    private Token token;
    private ParseNode left;
    private ParseNode right;

    public ParseNode(Token token,ParseNode left, ParseNode right) {
        this.left = left;
        this.right = right;
        this.token = token;
    }

    public ParseNode getLeft() {
        return left;
    }

    public ParseNode getRight() {
        return right;
    }

    public Token getToken() {
        return token;
    }

}
