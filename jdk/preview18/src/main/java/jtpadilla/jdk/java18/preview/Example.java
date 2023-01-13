package jtpadilla.jdk.java18.preview;

public class Example {

    sealed interface S permits A, B, C {}
    static final class A implements S {}
    static final class B implements S {}
    record C(int i) implements S {}  // Implicitly final

    static int testSealedExhaustive(S s) {
        return switch (s) {
            case A a -> 1;
            case B b -> 2;
            case C c -> 3;
        };
    }
    public static void main(String[] args) {
        System.out.println(testSealedExhaustive(new B()));
    }

}
