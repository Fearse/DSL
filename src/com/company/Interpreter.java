package com.company;

import java.util.HashMap;

public class Interpreter {
    public static HashMap<String, HashSet> hashSetMap=new HashMap<>();
    public static HashMap<String, LinkedList> linkedListHashMap=new HashMap<>();
    public static HashMap<String,String> scope=new HashMap<>();

    public String run(Node node){
        if (node.getClass()==UnOpNode.class) {
            if (((UnOpNode) node).operator.type.typeName.equals("PRINT")) {
                if (((UnOpNode) node).value.getClass() == VarNode.class) {
                    VarNode a = (VarNode) ((UnOpNode) node).value;
                    if (linkedListHashMap.containsKey(a.var.value)) {
                        linkedListHashMap.get(a.var.value).print();
                    } else if (hashSetMap.containsKey(a.var.value))
                        hashSetMap.get(a.var.value).print();
                    else
                        System.out.println(this.run(((UnOpNode) node).value));
                }
                else System.out.println(this.run(((UnOpNode) node).value));
            }
            if (((UnOpNode) node).operator.type.typeName.equals("CLEAR")) {
                VarNode a = (VarNode) ((UnOpNode) node).value;
                if (linkedListHashMap.containsKey(a.var.value)) {
                    linkedListHashMap.get(a.var.value).clear();
                } else if (hashSetMap.containsKey(a.var.value))
                    hashSetMap.get(a.var.value).clear();
            }
        }
        if (node.getClass()==BinOpNode.class) {
            if (((BinOpNode) node).operator.type.typeName.equals("ASSIGN")) {
                    String res = this.run(((BinOpNode) node).rightVal);
                VarNode varNode=(VarNode)((BinOpNode) node).leftVal;
                    scope.put(varNode.var.value, res);
                    return res;
            }
            else if (((BinOpNode) node).operator.type.typeName.equals("GET")||((BinOpNode) node).operator.type.typeName.equals("ADD")||
                    ((BinOpNode) node).operator.type.typeName.equals("CONTAINS")||((BinOpNode) node).operator.type.typeName.equals("DELETE")){
                VarNode varNode=(VarNode)((BinOpNode) node).leftVal;
                if (hashSetMap.containsKey(varNode.var.value)) {
                    int rightValue = Integer.parseInt(run(((BinOpNode) node).rightVal));
                    String test="";
                    switch (((BinOpNode) node).operator.type.typeName) {
                        case "ADD":
                            hashSetMap.get(varNode.var.value).add(rightValue);
                            return "";
                        case "GET":
                            System.out.println(hashSetMap.get(varNode.var.value).get(rightValue));
                            return "";
                        case "CONTAINS":
                            System.out.println(hashSetMap.get(varNode.var.value).contains(rightValue));
                            return "";
                        case "DELETE":
                            hashSetMap.get(varNode.var.value).delete(rightValue);
                            return "";
                    }
                }
                else if (linkedListHashMap.containsKey(varNode.var.value)){
                    int rightValue = Integer.parseInt(run(((BinOpNode) node).rightVal));
                    String test="";
                    switch (((BinOpNode) node).operator.type.typeName) {
                        case "ADD":
                            linkedListHashMap.get(varNode.var.value).add(rightValue);
                            return "";
                        case "GET":
                            System.out.println(linkedListHashMap.get(varNode.var.value).get(rightValue));
                            return "";
                        case "CONTAINS":
                            System.out.println(linkedListHashMap.get(varNode.var.value).contains(rightValue));
                            return "";
                        case "DELETE":
                            linkedListHashMap.get(varNode.var.value).delete(rightValue);
                            return "";
                    }
                }
            }
            else
            {
                int left=Integer.parseInt(this.run(((BinOpNode) node).leftVal));
                int right=Integer.parseInt(this.run(((BinOpNode) node).rightVal));
                return switch (((BinOpNode) node).operator.type.typeName) {
                    case "PLUS" -> String.valueOf(left + right);
                    case "MINUS" -> String.valueOf(left - right);
                    case "MULT" -> String.valueOf(left * right);
                    case "DIV" -> String.valueOf(left / right);
                    default -> throw new Error("Недопустимая операция");
                };
            }
        }
        if (node.getClass()==VarNode.class) {
            return scope.get(((VarNode) node).var.value);
        }
        if (node.getClass()==NumberNode.class) {
            return ((NumberNode) node).number.value;
        }
        if (node.getClass()==IfNode.class){
            int left=Integer.parseInt(this.run(((IfNode) node).leftVal));
            int right=Integer.parseInt(this.run(((IfNode) node).rightVal));
            switch (((IfNode) node).operator.type.typeName) {
                case "LESS":
                    if (left < right) {
                        for (int i = 0; i < ((IfNode) node).thenOperations.size(); i++)
                            this.run(((IfNode) node).thenOperations.get(i));
                    }
                    else{
                        for (int i = 0; i < ((IfNode) node).elseOperations.size(); i++)
                            this.run(((IfNode) node).elseOperations.get(i));
                    }
                    break;
                case "MORE":
                    if (left > right) {
                        for (int i = 0; i < ((IfNode) node).thenOperations.size(); i++)
                            this.run(((IfNode) node).thenOperations.get(i));
                    }
                    else{
                        for (int i = 0; i < ((IfNode) node).elseOperations.size(); i++)
                            this.run(((IfNode) node).elseOperations.get(i));
                    }
                    break;
                case "EQUAL":
                    if (left == right) {
                        for (int i = 0; i < ((IfNode) node).thenOperations.size(); i++)
                            this.run(((IfNode) node).thenOperations.get(i));
                    }
                    else{
                        for (int i = 0; i < ((IfNode) node).elseOperations.size(); i++)
                            this.run(((IfNode) node).elseOperations.get(i));
                    }
                    break;
            }
        }
        if (node.getClass()==WhileNode.class){
            int left=Integer.parseInt(this.run(((WhileNode) node).leftVal));
            int right=Integer.parseInt(this.run(((WhileNode) node).rightVal));
            switch (((WhileNode) node).operator.type.typeName) {
                case "LESS":
                    while (left < right) {
                        for (int i = 0; i < ((WhileNode) node).operations.size(); i++)
                            this.run(((WhileNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileNode) node).leftVal));
                        right = Integer.parseInt(this.run(((WhileNode) node).rightVal));
                    }
                    break;
                case "MORE":
                    while (left > right) {
                        for (int i = 0; i < ((WhileNode) node).operations.size(); i++)
                            this.run(((WhileNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileNode) node).leftVal));
                        right = Integer.parseInt(this.run(((WhileNode) node).rightVal));
                    }
                    break;
                case "EQUAL":
                    while (left == right) {
                        for (int i = 0; i < ((WhileNode) node).operations.size(); i++)
                            this.run(((WhileNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileNode) node).leftVal));
                        right = Integer.parseInt(this.run(((WhileNode) node).rightVal));
                    }
                    break;
            }
        }
        if (node.getClass()==ForNode.class){
            int left=Integer.parseInt(this.run(((ForNode) node).leftVal));
            int right=Integer.parseInt(this.run(((ForNode) node).rightVal));
            switch (((ForNode) node).operator.type.typeName) {
                case "LESS":
                    while (left < right) {
                        for (int i = 0; i < ((ForNode) node).operations.size(); i++)
                            this.run(((ForNode) node).operations.get(i));
                        this.run(((ForNode) node).action);
                        left = Integer.parseInt(this.run(((ForNode) node).leftVal));
                        right = Integer.parseInt(this.run(((ForNode) node).rightVal));
                    }
                    break;
                case "MORE":
                    while (left > right) {
                        for (int i = 0; i < ((ForNode) node).operations.size(); i++)
                            this.run(((ForNode) node).operations.get(i));
                        this.run(((ForNode) node).action);
                        left = Integer.parseInt(this.run(((ForNode) node).leftVal));
                        right = Integer.parseInt(this.run(((ForNode) node).rightVal));
                    }
                    break;
                case "EQUAL":
                    while (left == right) {
                        for (int i = 0; i < ((ForNode) node).operations.size(); i++)
                            this.run(((ForNode) node).operations.get(i));
                        this.run(((ForNode) node).action);
                        left = Integer.parseInt(this.run(((ForNode) node).leftVal));
                        right = Integer.parseInt(this.run(((ForNode) node).rightVal));
                    }
                    break;
            }
        }
        if (node.getClass()==InitNode.class){
            VarNode varNode=(VarNode)((InitNode)node).var;
            switch(((InitNode) node).type.type.typeName){
                case "HASHSET":
                    hashSetMap.put(varNode.var.value,new HashSet());
                    return "";
                case "LINKEDLIST":
                    linkedListHashMap.put(varNode.var.value,new LinkedList());
                    return "";
            }
        }
        return "";
    }
}
