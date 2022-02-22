package jtpadilla.pattermatchinginstanceof;

public class Example {

    public static void main(String[] args) {
        checkObject("Hello");
        checkObject(1);
    }

    static private void checkObject(Object o) {
        if (o instanceof String s) {
            System.out.println("Es una String: " + s);
        } else {
            System.out.println("No una String");
        }
    }

}
