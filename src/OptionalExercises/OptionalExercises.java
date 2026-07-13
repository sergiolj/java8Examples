package OptionalExercises;

import java.util.Optional;

class Client {
    ShippingAddress shippingAddress;
    ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Uso do Optional para evitar a necessidade dos testes com if de antes do Java 8.
     * @param client
     * @return
     */
    public static String retrieveCityNameFromClientAddress(Client client) {
        return Optional.ofNullable(client)
                .map(Client::getShippingAddress)
                .map(ShippingAddress::getCity)
                .map(City::getName)
                .orElse("Endereço inválido.");
    }
}

class ShippingAddress {
    City city;
    City getCity() { return city; }
}

class City {
    String name;
    String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}

public class OptionalExercises{
    public static void main(String[] args) {

        String cityToCalculateShipping;

        City city = new City();
        ShippingAddress shippingAddress = new ShippingAddress();
        Client client = new Client();
        shippingAddress.city = city;
        //city.name = "Salvador";

        client.shippingAddress = shippingAddress;


        //O compilador não sabe que o cliente não tem endereço cadastrado e irá retornar um NullPointerException
        // quebranddo o código.
        //String cityToCalculateShipping = client.shippingAddress.city.toString();

        //Para evitar, antes do Java 8 era preciso testar todos os campos para verificar se algum era null.

//        if(client != null) {
//            ShippingAddress address = client.shippingAddress;
//            if (address != null) {
//                City cityAddress = address.city;
//                if(cityAddress != null){
//                    String name = cityAddress.name;
//                    if(name != null){
//                        cityToCalculteShipping = name;
//                        System.out.println("Cidade para calcular frete: " + cityToCalculteShipping);
//                    }else System.out.println("Nome da cidade não cadastrado.");
//                }else System.out.println("Cidade não cadastrada.");
//            }else System.out.println("Endereço não cadastrado.");
//        }else System.out.println("Cliente não cadastrado.");

        cityToCalculateShipping = Client.retrieveCityNameFromClientAddress(client);
        System.out.println("Cidade para calcular frete: " + cityToCalculateShipping);
    }
}
