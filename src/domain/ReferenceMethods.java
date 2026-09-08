package domain;
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
class Validator {
    public boolean isEmailValido(String email) {
        return email != null && email.contains("@");
    }
}

class User{
    private String name;
    public User(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Usuário ["+name+"]";
    }
}

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
        exercise01();
        exercise02();
        exercise03();
        exercise04();

    }


    public static void exercise01() {
        System.out.println("01 - Exercício de refatoração com Reference Methods");
        List<String> precosEmTexto = Arrays.asList("15.50", "20.00", "9.99");

        // SUA MISSÃO: Substituir a lambda no .map() por um Method Reference
        List<Double> precos = precosEmTexto.stream()
                .map(preco -> Double.parseDouble(preco))
                .collect(Collectors.toList());

        List<Double> prices = precosEmTexto.stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        System.out.println(precos);
        System.out.println(prices);
    }

    public static void exercise02() {
        System.out.println("02 - Exercício de refatoração com Reference Methods");
        List<String> palavras = Arrays.asList("Java", "Lambda", "Stream", "API");

        // SUA MISSÃO: Substituir a lambda no .map() por um Method Reference
        List<Integer> tamanhos = palavras.stream()
                .map(palavra -> palavra.length())
                .collect(Collectors.toList());

        List<Integer> wordSizes = palavras.stream()
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println(tamanhos);
        System.out.println(wordSizes);
    }

    public static void exercise03 (){
        System.out.println("03 - Exercício de refatoração com Reference Methods e instância de um objeto");
        Validator emailValidator = new Validator();
        List<String> emails = Arrays.asList("teste@gmail.com", "invalido.com", "ola@empresa.br");

        // SUA MISSÃO: Substituir a lambda no .filter() por um Method Reference
        List<String> emailsValidos = emails.stream()
                .filter(email -> emailValidator.isEmailValido(email))
                .collect(Collectors.toList());

        List<String> validEmails = emails.stream()
                .filter(emailValidator::isEmailValido)
                .toList();

        System.out.println(emailsValidos);
        System.out.println(validEmails);
    }

    public static void exercise04(){
        List<String> nomes = Arrays.asList("Alice", "Bob", "Carlos");

        // SUA MISSÃO: Substituir a lambda no .map() por um Method Reference de Construtor
        List<User> usuarios = nomes.stream()
                .map(nome -> new User(nome))
                .collect(Collectors.toList());


        List<User> userList = nomes.stream()
                .map(User::new)
                .collect(Collectors.toList());

        System.out.println(usuarios);
        System.out.println(userList);
    }


}
