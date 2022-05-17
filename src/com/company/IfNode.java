package com.company;

import java.util.ArrayList;

public class IfNode extends Node {
    Token operator;
    Node leftVal;
    Node rightVal;
    public ArrayList<Node> thenOperations = new ArrayList<>();
    public ArrayList<Node> elseOperations = new ArrayList<>();
    public IfNode(Token operator, Node leftVal, Node rightVal) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
    }
    public void addThenOperations(Node op){
        thenOperations.add(op);
    }
    public void addElseOperations(Node op){
        elseOperations.add(op);
    }
}
