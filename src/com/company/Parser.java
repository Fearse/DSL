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
        if (TokenType.tokenTypeList[20].equals(tokens.get(pos).type)){
            pos++;
            return new VarNode(tokens.get(pos-1));
        }
         throw new Error("Ожидается переменная или число на позиции: "+pos);
    }
    public Node parsePar(){
        if (TokenType.tokenTypeList[16].equals(tokens.get(pos).type)) {
            pos++;
            Node node = parseFormula();
            need(new TokenType[]{TokenType.tokenTypeList[17]});
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
       if (TokenType.tokenTypeList[20].equals(tokens.get(pos).type)) {
           Node varNode = parseVarNum();
           Token assign = receive(new TokenType[]{TokenType.tokenTypeList[4]});
           if (assign != null) {
               Node rightVal = parseFormula();
               return new BinOpNode(assign, varNode, rightVal);
           }
           throw new Error("После переменной ожидается = на позиции:"+pos);
       }
       else if (TokenType.tokenTypeList[12].equals(tokens.get(pos).type)){
           pos++;
           return new UnOpNode(tokens.get(pos-1), this.parseFormula());
       }
       else if(TokenType.tokenTypeList[14].equals(tokens.get(pos).type)){
           pos++;
           return  parseWhile();
       }
       else if(TokenType.tokenTypeList[13].equals(tokens.get(pos).type))
       {
           pos++;
           return parseFor();
       }
       throw new Error("Ошибка на позиции: "+pos+". Ожидалось действие или переменная");
   }
    public Node parseFor(){
       Node leftVal=parseFormula();
       Token operator=receive(new TokenType[]{TokenType.tokenTypeList[9],TokenType.tokenTypeList[10],TokenType.tokenTypeList[11]});
       Node rightVal=parseFormula();

       need(new TokenType[]{TokenType.tokenTypeList[15]});

       Node varNode = parseVarNum();
       Token assign = receive(new TokenType[]{TokenType.tokenTypeList[4]});
       Node rightActVal = parseFormula();
       BinOpNode action= new BinOpNode(assign, varNode, rightActVal);
       if (assign == null)
           throw new Error("После переменной ожидается = на позиции:"+pos);
       ForNode forNode= new ForNode(operator,leftVal,rightVal,action);
       need(new TokenType[]{TokenType.tokenTypeList[18]});
       while(!TokenType.tokenTypeList[19].equals(tokens.get(pos).type)) {
           forNode.addOperations(getOperations());
           if (pos==tokens.size())
               throw new Error("Ошибка, ожидалось }");
       }
       pos++;
       return forNode;
   }
    public Node parseWhile(){
        Node leftVal=parseFormula();
        Token operator=receive(new TokenType[]{TokenType.tokenTypeList[9],TokenType.tokenTypeList[10],TokenType.tokenTypeList[11]});
        Node rightVal=parseFormula();
        WhileNode whileNode=new WhileNode(operator,leftVal,rightVal);
        need(new TokenType[]{TokenType.tokenTypeList[18]});
        while(!TokenType.tokenTypeList[19].equals(tokens.get(pos).type)) {
            whileNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("Ошибка, ожидалось }");
        }
        pos++;
        return whileNode;
    }
    public Node getOperations(){
        Node codeStringNode=parseString();
        need(new TokenType[]{TokenType.tokenTypeList[15]});//;
        return codeStringNode;
    }
    public RootNode parseTokens(){
        RootNode root=new RootNode();
        while (pos<tokens.size()){
            Node codeStringNode= parseString();
            need(new TokenType[]{TokenType.tokenTypeList[15]});//;
            root.addNode(codeStringNode);
        }
        return root;
   }
}
