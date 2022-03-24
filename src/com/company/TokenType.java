package com.company;

public class TokenType {
    String typeName;
    String reg;
    public static TokenType[] tokenTypeList={
            new TokenType("Number", "^0|[1-9][0-9]*"),

            new TokenType("Space", "\\ "),
            new TokenType("EndL", "[\\n]"),
            new TokenType("Karetka", "[\\r]"),

            new TokenType("Assign", "[=]"),
            new TokenType("Plus", "[+]"),
            new TokenType("Minus", "[-]"),
            new TokenType("Multiply", "[*]"),
            new TokenType("Division", "[/]"),

            new TokenType("Print", "(?i)print"),
            new TokenType("For", "(?i)for"),

            new TokenType("END", "[;]"),
            new TokenType("LPAR", "[(]"),
            new TokenType("RPAR", "[)]"),

            new TokenType("Variable", "[a-z][a-z]*"),
    };
    public TokenType(String typeName, String reg) {
        this.typeName = typeName;
        this.reg = reg;
    }
}
