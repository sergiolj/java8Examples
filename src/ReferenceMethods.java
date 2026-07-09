import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modificação implementada no Java 8 que permite substituir uma expressão lambda que não faz absolutamente
 * nada além de chamar um método que já existe por um Method Reference.
 * Sintaxe ::
 * Existem 4 tipos de Method Reference
 */
public class ReferenceMethods {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> numbersAsString = new ArrayList<>();

        numbers.forEach(n -> numbersAsString.add("Nro " + String.valueOf(n)));

        numbers.stream()
                .map(String::valueOf)
                .forEach(numbersAsString::add);

        System.out.println("Imprimindo lista de números como strings.");
        System.out.println(numbersAsString);

        //Cria uma lista de strings
        List<String> numbersAsString2 = new ArrayList<>();
        //Copia os valores da lista de números para a lista de strings que pode receber alterações.
        numbersAsString2 = numbers.stream().map(String::valueOf).collect(Collectors.toList());
        numbersAsString2.add("6");

        //Usa a lista de strings como referência para criar uma nova lista de inteiros imutável (toList() Java16)
        List<Integer> convertFromString = numbersAsString2.stream().map(Integer::parseInt).toList();
        System.out.println(convertFromString);

    }
}
