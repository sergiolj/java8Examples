package OptionalExercises;

import lombok.Getter;

import java.util.Optional;

@Getter
class User {
    private Profile profile;
    public User(Profile profile) { this.profile = profile; }
}

@Getter
class Profile {
    private Coupon coupon;
    public Profile(Coupon coupon) { this.coupon = coupon; }
}

@Getter
class Coupon {
    private Double value;
    public Coupon(Double value) { this.value = value; }
}


public class DiscountCalc {
    Double getDiscountFromUser(User user) {
        Double discount = 0.0; // Valor padrão

        if (user != null) {
            Profile profile = user.getProfile();
            if (profile != null) {
                Coupon discountCoupon = profile.getCoupon();
                if (discountCoupon != null) {
                    Double couponValue = discountCoupon.getValue();
                    if (couponValue != null) {
                        discount = couponValue;
                    }
                }
            }
        }

        return discount;
    }

    /**
     * Usa o Optional para solucionar a questão dos possíveis NullPointerException internamente no método e devolvendo
     * apenas o valor Double caso ele exista, .map(Coupon::getValue) ou 0.0 se ele não existir.
     * @param user
     * @return
     */
    Double getDoubleDiscountFromUser(User user){
        return  // 1. Aqui, nós criamos a caixa.
                // Tipo retornado: Optional<User>
                Optional.ofNullable(user)

                // 2. O map pega o User, extrai o Profile, e o coloca em uma nova caixa.
                // Tipo retornado: Optional<Profile>
                .map(User::getProfile)

                 // 3. O map pega o Profile, extrai o Coupon, e o coloca em uma nova caixa.
                 // Tipo retornado: Optional<Coupon>
                .map(Profile::getCoupon)

                 // 4. O map pega o Coupon, extrai o Double (valor), e o coloca em uma nova caixa.
                 // É AQUI que o tipo passa a ser Double, mas ele ainda está PRESO no Optional.
                 // Tipo retornado: Optional<Double>
                .map(Coupon::getValue)

                 // 5. O orElse é uma "operação terminal". A função dele é: "Abra o Optional<Double>.
                 // Se tiver um número, me devolva ele puro. Se estiver vazio, me devolva 0.0".
                 // Tipo retornado: Double
                .orElse(0.0);
    }

    Optional<Double> getOptionalDiscountFromUser(User user){
        return Optional.ofNullable(user)
                        .map(User::getProfile)
                        .map(Profile::getCoupon)
                        .map(Coupon::getValue);
    }

    public static void main(String[] args) {
        DiscountCalc calc = new DiscountCalc();

        User userWithCoupon = new User(new Profile(new Coupon(15.5)));
        User userWithoutCoupon = new User(new Profile(null));
        User userWithoutProfile = new User(null);

        System.out.println("Desconto User 1 (Esperado 15.5): " + calc.getDiscountFromUser(userWithCoupon));
        System.out.println("Desconto User 2 (Esperado 0.0): " + calc.getDiscountFromUser(userWithoutCoupon));
        System.out.println("Desconto User 3 (Esperado 0.0): " + calc.getDiscountFromUser(userWithoutProfile));
        System.out.println("Desconto Nulo (Esperado 0.0): " + calc.getDiscountFromUser(null));


        System.out.println("Desconto User 1 (Esperado 15.5): " + calc.getDoubleDiscountFromUser(userWithCoupon));
        System.out.println("Desconto User 2 (Esperado 0.0): " + calc.getDoubleDiscountFromUser(userWithoutCoupon));
        System.out.println("Desconto User 3 (Esperado 0.0): " + calc.getDoubleDiscountFromUser(userWithoutProfile));
        System.out.println("Desconto Nulo (Esperado 0.0): " + calc.getDoubleDiscountFromUser(null).toString());

        System.out.println("Desconto User 1 (Esperado 15.5): " + calc.getOptionalDiscountFromUser(userWithCoupon).orElse(0.0));
        System.out.println("Desconto User 2 (Esperado 0.0): " + calc.getOptionalDiscountFromUser(userWithoutCoupon).orElse(0.0));

        calc.getOptionalDiscountFromUser(userWithCoupon).ifPresent(System.out::println);
        calc.getOptionalDiscountFromUser(userWithCoupon)
                .ifPresent(value-> System.out.println("Parabéns, você ganhou um desconto de: " + value));

        calc.getOptionalDiscountFromUser(userWithoutCoupon)
                .ifPresentOrElse(value-> System.out.println("Parabéns, você ganhou um desconto de: " + value),
                        () -> System.out.println("Poxa, você não está habilitado para descontos."));

    }
}
