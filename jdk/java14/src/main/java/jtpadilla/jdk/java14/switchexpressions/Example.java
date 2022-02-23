package jtpadilla.jdk.java14.switchexpressions;

public class Example {

    public static void main(String[] args) {
        System.out.println(toText(3));
    }

    private static String toText(int number) {
        return switch (number) {
            case 0 -> "cero";
            case 1 -> "uno";
            case 2 -> "dos";
            case 3 -> {
                yield "tres";
            }
            default -> "superior a tres";
        };
    }

}
