package com.company;

public class LinkedList {
    public Node head;

    public LinkedList() {
        head = null;
    }

    public LinkedList(Object data) {
        head = new Node(data);
    }

    public void add(Object data) {
        if (head != null) {
            head.appendToTail(data);
        } else {
            head = new Node(data);
        }
    }

    public boolean contains(Object data) {
        Node current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            else {
                current=current.next;
            }
        }
        return false;
    }

    public Object get(int data){
        Node current = head;
        for(int i=0;i<data;i++){
            if(current==null){
                return -1;
            }
            else{
                current=current.next;
            }
        }
        return current.data;
    }
    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + "->");
            current = current.next;
        }
        System.out.print("null");
        System.out.println("");
    }
    public void clear(){
        head.next=null;
        head=null;
    }
    public void delete(Object d) {
        Node newHead = new Node(0);
        newHead.next = head;
        Node previous = newHead;
        Node current = head;

        while (current != null) {
            if (current.data.equals(d)) {
                previous.next = current.next;
            } else {
                previous = current;
            }
            current = current.next;
        }

        head = newHead.next;

    }
    public class Node {
        Node next = null;
        Object data;

        public Node(Object data) {
            this.data = data;
        }

        void appendToTail(Object d) {

            Node end = new Node(d);
            Node n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = end;

        }
    }
}
