package com.xoom.blog.prattparser;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class PrattParser {

    private SimpleLexer simpleLexer;
    private Map<String,Integer> variables;

    private static EnumMap<TokenType, ExpressionParser> tokenParserMapping = new EnumMap<TokenType, ExpressionParser>(TokenType.class);
    private static DefaultPrefixExpressionParser  defaultPrefixExpressionParser = new DefaultPrefixExpressionParser();
    private static NodePrefixExpressionParser nodePrefixExpressionParser = new NodePrefixExpressionParser();
    private static NodeInfixExpressionParser nodeInfixExpressionParser = new NodeInfixExpressionParser();
    private static DummyExpressionParser dummyExpressionParser = new DummyExpressionParser();

    static  {
        tokenParserMapping.put(TokenType.LEFTPAREN, new LeftparenPrefixExpressionParser());
        tokenParserMapping.put(TokenType.NUMBER, defaultPrefixExpressionParser);
        tokenParserMapping.put(TokenType.IDENTIFIER, defaultPrefixExpressionParser);
        tokenParserMapping.put(TokenType.PLUS, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.MINUS, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.MULTIPLY, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.GREATER, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.GREATEREQUAL, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.LESSER, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.LESSEREQUAL, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.EQUAL, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.NOTEQUAL, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.AND, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.OR, nodeInfixExpressionParser);
        tokenParserMapping.put(TokenType.NOT, nodePrefixExpressionParser);
        tokenParserMapping.put(TokenType.END, dummyExpressionParser);
    }

    private TokenParser nextTokenParser;

    /**
     * The main parse method
     * @param startPrecedence
     * @return
     */
    public ParseNode parse(int startPrecedence) {
        TokenParser current = nextTokenParser;
        nextTokenParser =  nextTokenParser();
        ParseNode left = current.getExpressionParser().parseExpression(this, current.getToken());
        while (startPrecedence < nextTokenParser.getToken().getTokenType().getPrecedenceLevel())  {
            current = nextTokenParser;
            nextTokenParser =  nextTokenParser();
            left = current.getExpressionParser().parseExpressionWithLeft(this, current.getToken(), left );
        }

        return left;
    }

    public void nextMatch(TokenType tokenType) {
        if (nextTokenParser.getToken().getTokenType() !=  tokenType)  {
            throw new IllegalStateException(" Token " + tokenType + " is not found as expected") ;
        }
        nextTokenParser = nextTokenParser();
    }

    public TokenParser nextTokenParser() {
        Token tok = simpleLexer.next();
        ExpressionParser expressionParser = tokenParserMapping.get(tok.getTokenType());
        if (expressionParser == null ) {
            expressionParser = dummyExpressionParser;
        }
        TokenParser tokenParser = new TokenParser(expressionParser, tok) ;
        return tokenParser;
    }


    public ParseNode evaluate(String input, Map<String,Integer> variables) {
        this.variables = variables;
        simpleLexer = new SimpleLexer(input);
        nextTokenParser = nextTokenParser();
        return parse(0);
    }

    public Boolean eval(String input, Map<String,Integer> variables)   {
        ParseNode node = evaluate(input,variables);
        Object result = evaluateTree( node);
        if (result instanceof Integer) {
            Integer r = (Integer)result;
            return r > 0;
        } else {
            return (Boolean)result;
        }

    }

    public Object evaluateTree(ParseNode node) {
        TokenType tokenType = node.getToken().getTokenType();
        Object result = null;
        switch(tokenType) {
            case NUMBER:
                result = Integer.valueOf(node.getToken().getValue());
                break;
            case IDENTIFIER:
                result = variables.get(node.getToken().getValue());
                break;
            case PLUS:
                result = (Integer)evaluateTree(node.getLeft()) + (Integer)evaluateTree(node.getRight()) ;
                break;
            case MINUS:
                result = (Integer)evaluateTree(node.getLeft()) - (Integer)evaluateTree(node.getRight()) ;
                break;
            case MULTIPLY:
                result = (Integer)evaluateTree(node.getLeft()) * (Integer)evaluateTree(node.getRight()) ;
                break;
            case GREATER:
                result = (Integer)evaluateTree(node.getLeft()) >  (Integer)evaluateTree(node.getRight());
                break;
            case GREATEREQUAL:
                result = (Integer)evaluateTree(node.getLeft()) >=  (Integer)evaluateTree(node.getRight());
                break;
            case LESSER:
                result = (Integer)evaluateTree(node.getLeft()) <  (Integer)evaluateTree(node.getRight());
                break;
            case LESSEREQUAL:
                result = (Integer)evaluateTree(node.getLeft()) <=  (Integer)evaluateTree(node.getRight());
                break;
            case EQUAL:
                result = (Integer)evaluateTree(node.getLeft()) ==  (Integer)evaluateTree(node.getRight());
                break;
            case NOTEQUAL:
                result = (Integer)evaluateTree(node.getLeft()) !=  (Integer)evaluateTree(node.getRight());
                break;
            case AND:
                result = (Boolean)evaluateTree(node.getLeft()) &&  (Boolean)evaluateTree(node.getRight());
                break;
            case OR:
                result = (Boolean)evaluateTree(node.getLeft()) ||  (Boolean)evaluateTree(node.getRight());
                break;
            case NOT:
                result = !(Boolean)evaluateTree(node.getLeft());
                break;
        }
        return result;
    }
}
