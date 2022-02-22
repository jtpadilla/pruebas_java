package jtpadilla.textblocks;

public class NewEscapeSequences {

    static final private String TEXT1 = """
                Lorem ipsum dolor sit amet, consectetur adipiscing \
                elit, sed do eiusmod tempor incididunt ut labore \
                et dolore magna aliqua.\
                """;

    static final private String TEXT2 = """
            red  \s
            green\s
            blue \s
            """;
            
    public static void main(String[] args) {

        System.out.println(TEXT1);
        System.out.println("=====================================================");
        System.out.println(TEXT2);
        System.out.println("=====================================================");

    }

}
