package jtpadilla.textblocks;

public class IncidentalWhiteSpace {

    static final private String TEXT1 = """
            Hola que tal esta
            tu esta mañana.
            Yo estoy bien.
            """;

    static final private String TEXT2 =
            """
            String text = \"""
                A text block inside a text block
            \""";
            """;

    static final private String TEXT3 =
            """
            A common character
            in Java programs
            is \"""";

    static final private String TEXT4 =
            """
            The empty string literal
            is formed from " characters
            as follows: "\"""";

    static final private String TEXT5 = """
             1 "
             2 ""
             3 ""\"
             4 ""\""
             5 ""\"""
             6 ""\"""\"
             7 ""\"""\""
             8 ""\"""\"""
             9 ""\"""\"""\"
            10 ""\"""\"""\""
            11 ""\"""\"""\"""
            12 ""\"""\"""\"""\"
        """;

    public static void main(String[] args) {
        System.out.println(TEXT1);
        System.out.println("=====================================================");
        System.out.println(TEXT2);
        System.out.println("=====================================================");
        System.out.println(TEXT3);
        System.out.println("=====================================================");
        System.out.println(TEXT4);
        System.out.println("=====================================================");
        System.out.println(TEXT5);
    }

}
