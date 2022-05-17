package com.company;

import java.util.Arrays;

public class HashSet {
    private Object[] a;

    public HashSet() {
        a = new Object[2];
    }

    public boolean tryAdd(Object[] a, Object data) {
        int pos = data.hashCode() % a.length;
        for (int i = 0; i < a.length; i++) {
            if (a[pos] == null) {
                a[pos] = data;
                return true;
            } else {
                pos++;
                if (pos == a.length)
                    pos = 0;
            }
        }
        return false;
    }
    public Object get(int pos){
        return a[pos];
    }
    public boolean contains(Object data){
        int pos = data.hashCode() % a.length;
        for (int i = 0; i < a.length; i++) {
            if (a[pos] == data) {
                return true;
            } else {
                pos++;
                if (pos == a.length)
                    pos = 0;
            }
        }
        return false;
    }
    public void delete(Object data){
        int pos = data.hashCode() % a.length;
        for (int i = 0; i < a.length; i++) {
            if (a[pos] == data) {
                a[pos]=null;
                return;
            } else {
                pos++;
                if (pos == a.length)
                    pos = 0;
            }
        }
    }
    public void clear(){
        Arrays.fill(a, null);
        a=new Object[2];
    }
    public void print(){
        System.out.print("{");
        for (int i=0;i<a.length-1;i++)
            if(a[i]!=null)
                System.out.print(a[i]+", ");
        System.out.print(a[a.length-1]+"}");
        System.out.println("");
    }
    public void add(Object data) {
        if (!tryAdd(a, data)) {
            resize();
            tryAdd(a, data);
        }
    }

    public void resize() {
        Object[] b = new Object[a.length * 2];
        for (int i = 0; i < a.length; i++) {
            tryAdd(b, a[i]);
        }
        a = b;
    }
}
