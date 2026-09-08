package domain;
import java.util.Arrays;
import java.util.List;

interface TextFormatter{
    String format (String value);

}

public class ReturnStringLambda {
    public static void main(String[] args) {
        TextFormatter upperCase = (value)-> value.toUpperCase();
        String toFormat = "asdafe";
        toFormat = upperCase.format(toFormat);
        System.out.println(toFormat);

        List<String> listToFormat = Arrays.asList("HAGSeasa", "AErreads", "pooPOOQD");

        listToFormat.replaceAll(word -> upperCase.format(word));
        System.out.println(listToFormat);
    }
}
