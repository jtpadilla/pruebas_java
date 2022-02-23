package jtpadilla.jdk.java16.records;

record  MyRecord(int x, int y) {}

public class Example {

    public static void main(String[] args) {
        System.out.println(new MyRecord(10, 20));
    }

}
