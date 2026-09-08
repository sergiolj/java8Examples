package domain;

@FunctionalInterface
interface Counting{
    void set (int a, int b);
}

public class CountDownLambda {
    public static void main(String[] args) {
        Counting counter = (a, b) -> {
            do {
                System.out.print(a + " ");
                a--;
                waitASecond();
            }while (a!=(b-1));
        };

       System.out.println("Contador usando uma expressão lambda que implementa uma interface funcional.");
       counter.set(10,1);

       Runnable counter2 = ()->{
            for(int i=10; i>0; i--){
                System.out.print(i + " ");
                waitASecond();
            }
       };

       System.out.println("\nContador usando uma expressão lambda que implementa um interface Runnable.");
       counter2.run();

       System.out.println("\nContador usando uma expressão lambda que usa um método estático para " +
               "criar um interface Runnable e injetar em uma Thread.");
       Thread counter3 = new Thread(customCountDown(10,1));
       counter3.start();
    }

    public static Runnable customCountDown(int begin, int end){
        return ()->{
            int a = begin;
            do {
                System.out.print(a + " ");
                a--;
                waitASecond();
            }while (a!=(end-1));
        };
    }

    private static void waitASecond() {
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
