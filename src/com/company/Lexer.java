package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    String code;
    int pos=0;
    ArrayList<Token> tokenList=new ArrayList<>();

    public Lexer(String code) {
        this.code = code;
    }
    public List<Token> analyze(){
        while(findTokens()){}
        for (int i=0;i<tokenList.size();i++)
            if (!(tokenList.get(i).value.equals(" ")||tokenList.get(i).value.equals("\\r")))
                System.out.println(tokenList.get(i).type.typeName+" "+tokenList.get(i).value+" "+tokenList.get(i).pos);
        return this.tokenList;
    }
    public boolean findTokens(){
        TokenType[] tokenTypes=TokenType.tokenTypeList;
        if(this.pos>=code.length())
            return false;
        for (int i=0;i<tokenTypes.length;i++){
            TokenType tokenType=tokenTypes[i];
            String regex=tokenType.reg;
            Matcher matcher=Pattern.compile(regex).matcher(code);
            if(matcher.find(this.pos)&&matcher.start()==this.pos)
            {
                boolean is_new=true;
                String result=this.code.substring(this.pos,this.pos+matcher.group().length());
                Token token = new Token(tokenType, result, this.pos);
               for(int j=0;j<tokenList.size();j++)
                {
                    if (tokenList.get(j).value.equals(token.value)&&tokenList.get(j).pos==token.pos)
                    {
                        is_new=false;
                        break;
                    }
                }
                if (is_new) {
                    this.pos += result.length();
                    tokenList.add(token);
                }
                return true;
            }
        }
        throw new Error("Error on pos: "+this.pos);
    }
}