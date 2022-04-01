package com.company;

import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens;
    int pos=0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
    public Token receive(TokenType[] need){
        Token curToken;
        if (pos<tokens.size()) {
            curToken = tokens.get(pos);
            for (TokenType tokenType : need)
                if (tokenType.typeName.equals(curToken.type.typeName)) {
                    pos++;
                    return curToken;
                }
        }
        return null;
    }
    public void need(TokenType[] expected){
        Token token= receive(expected);
        if(token==null){
            throw new Error("На позииции "+pos+" ожидается "+expected[0].typeName);
        }
    }
    public Node parseVarNum(){
        if (TokenType.tokenTypeList[0].equals(tokens.get(pos).type)){
            pos++;
            return new NumberNode(tokens.get(pos-1));
        }
        if (TokenType.tokenTypeList[14].equals(tokens.get(pos).type)){
            pos++;
            return new VarNode(tokens.get(pos-1));
        }
         throw new Error("Ожидается переменная или число на позиции: "+pos);
    }
    public Node parsePar(){
        if (TokenType.tokenTypeList[12].equals(tokens.get(pos).type)) {
            pos++;
            Node node = parseFormula();
            need(new TokenType[]{TokenType.tokenTypeList[13]});
            return node;
        }
        else
            return parseVarNum();
    }
    public Node parseFormula(){
       Node leftVal= parsePar();
       Token operator= receive(new TokenType[]{TokenType.tokenTypeList[5],TokenType.tokenTypeList[6]});
       while (operator!=null){
           Node rightVal= parsePar();
           leftVal=new BinOpNode(operator,leftVal,rightVal);
           operator= receive(new TokenType[]{TokenType.tokenTypeList[5],TokenType.tokenTypeList[6]});
       }
       return leftVal;
   }
    public Node parseString(){
       if (TokenType.tokenTypeList[14].equals(tokens.get(pos).type)) {
           Node varNode = parseVarNum();
           Token assign = receive(new TokenType[]{TokenType.tokenTypeList[4]});
           if (assign != null) {
               Node rightVal = parseFormula();
               return new BinOpNode(assign, varNode, rightVal);
           }
           throw new Error("После переменной ожидается = на позиции:"+pos);
       }
       else if (TokenType.tokenTypeList[9].equals(tokens.get(pos).type)){
           pos++;
           return new UnOpNode(tokens.get(pos-1), this.parseFormula());//тк можно вывести не только число/вар, но и формулу
       }
       //добавить while
       throw new Error("Ошибка");
   }
    public RootNode parseTokens(){
        RootNode root=new RootNode();
        while (pos<tokens.size()){
            Node codeStringNode= parseString();
            need(new TokenType[]{TokenType.tokenTypeList[11]});//;
            root.addNode(codeStringNode);
        }
        return root;
   }
}
