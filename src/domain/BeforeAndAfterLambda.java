package domain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeforeAndAfterLambda {
    public static void main(String[] args) {
        Runnable beforeJ8 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Executando uma tarefa antes do Java 8, sem lambda.");
            }
        };
        beforeJ8.run();

        Runnable afterJ8 = () -> System.out.println("Executando uma tarefa depois do Java 8 com lambda.");
        afterJ8.run();

        //Exemplo 2.1
        OperationWithTwoVariables sumTwoVar = (a, b)-> a + b;
        OperationWithTwoVariables multiplyTwoVar = (a,b) -> a * b;
        int resultSum = sumTwoVar.calc(1,3);
        int resultMultiply = multiplyTwoVar.calc(1,3);
        System.out.println(resultSum + resultMultiply);


        //Exemplo 3
        //Usando interfaces funcionais nativas do Java
        List<String> languages = Arrays.asList("Java", "C", "Python");
        //O forEach() é o método default da interface funcional Iterable(I). Esse método recebe como parâmetro apenas um
        // um Consumer(I) que é uma outra interface funcional e cria um loop internamente com for que retorna void
        //A List (I) extende SequenceCollections(I) que extende Collections(I) e que extende Iterable(I)

        System.out.println("Eu me interesso pelas seguintes linguagens: ");

        languages.forEach((language) -> {
            System.out.print(language);
            System.out.print(", ");
        });
        //languages.forEach(System.out::println);

        //Exemplo 4
        //removeIf() é um método default da interface Collection que usa uma interface Predicate
        System.out.println();
        List<Integer> ages = new ArrayList<>(Arrays.asList(15,22,12,18,30));
        System.out.println(ages);
        ages.removeIf(age -> age<18);
        System.out.println(ages);

        //Exemplo 5
        List<String> names = Arrays.asList("Sérgio", "Nara", "Ana", "Milena");

        names.sort((n1, n2)-> n1.compareTo(n2));
        System.out.println(names);


    }

    //Exemplo 2
    @FunctionalInterface
    interface OperationWithTwoVariables {
        int calc(int x , int y);
    }
}