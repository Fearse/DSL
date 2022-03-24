package com.company;

import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static String checkFile(){
		String code="";
		try(FileReader reader = new FileReader("Code.txt"))
		{
			int c;
			while((c=reader.read())!=-1){

				code=code.concat(String.valueOf((char)c));
			}
		}
		catch(IOException ex){

			System.out.println(ex.getMessage());
		}
		return code;
	}

    public static void main(String[] args) {
	String s=checkFile();
	System.out.println(s);
	Lexer lexer=new Lexer(s);
	lexer.analyze();
    }
}
