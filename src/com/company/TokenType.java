package com.company;

public class TokenType {
    String typeName;
    String reg;
    public static TokenType[] tokenTypeList={
            new TokenType("Number", "^0|[1-9][0-9]*"),//0

            new TokenType("Space", "\\ "),//1
            new TokenType("EndL", "[\\n]"),//2
            new TokenType("Karetka", "[\\r]"),//3

            new TokenType("Assign", "[=]"),//4
            new TokenType("Plus", "[+]"),//5
            new TokenType("Minus", "[-]"),//6
            new TokenType("Multiply", "[*]"),//7 (ne realizovano)
            new TokenType("Division", "[/]"),//8 (ne realizovano)

            new TokenType("Less", "[<]"),//9
            new TokenType("More", "[>]"),//10
            new TokenType("Equal", "(?i)=="),//11

            new TokenType("Print", "(?i)print"),//12
            new TokenType("For", "(?i)for"),//13
            new TokenType("While","(?i)while"), //14(

            new TokenType("END", "[;]"),//15
            new TokenType("LPAR", "[(]"),//16
            new TokenType("RPAR", "[)]"),//17
            new TokenType("LRectPar", "[{]"),//18
            new TokenType("RRectPAR", "[}]"),//19

            new TokenType("Variable", "[a-z][a-z]*"),//20
            //float? string?
            //По-умному оформить массив токентайпов
    };
    public TokenType(String typeName, String reg) {
        this.typeName = typeName;
        this.reg = reg;
    }
}
