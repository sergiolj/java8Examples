package domain;

@FunctionalInterface
interface ButtonListener{
    void onClick();
}

public class ButtonExampleLambda {
    public static void main(String[] args) {
        ButtonListener listener = new ButtonListener() {
            @Override
            public void onClick() {
                System.out.println("Botão foi clicado!");
            }
        };
        listener.onClick();

        ButtonListener listenerLambda = ()-> System.out.println("Botão foi clicado Lambda!");
        listenerLambda.onClick();
    }
}


