package com.company;

public class TokenType {
    String typeName;
    String reg;
    public static TokenType[] tokenTypeList={
            new TokenType("Number", "^0|[1-9][0-9]*"),//0
            //добавить float

            new TokenType("Space", "\\ "),//1
            new TokenType("EndL", "[\\n]"),//2
            new TokenType("Karetka", "[\\r]"),//3

            new TokenType("Assign", "[=]"),//4
            new TokenType("Plus", "[+]"),//5
            new TokenType("Minus", "[-]"),//6
            new TokenType("Multiply", "[*]"),//7 (ne realizovano)
            new TokenType("Division", "[/]"),//8 (ne realizovano)

            new TokenType("Print", "(?i)print"),//9
            new TokenType("For", "(?i)for"),//10 (ne realizovano)
           // new TokenType("While","(?i)while"), (ne realizovano)

            new TokenType("END", "[;]"),//11
            new TokenType("LPAR", "[(]"),//12
            new TokenType("RPAR", "[)]"),//13

            new TokenType("Variable", "[a-z][a-z]*"),//14
            //нужны ли строки??
            //По-умному оформить массив токентайпов
            //Объявление типов переменной????
    };
    public TokenType(String typeName, String reg) {
        this.typeName = typeName;
        this.reg = reg;
    }
}
