package OptionalExercises;

class Client {
    CustomAddress customAddress;
    CustomAddress getEndereco() {
        return customAddress;
    }
}

class CustomAddress {
    City city;
    City getCity() { return city; }
}

class City {
    String name;
    String getName() { return name; }
}

public class OptionalExercises{
    public static void main(String[] args) {

        City city = new City();
        CustomAddress customAddress = new CustomAddress();
        Client client = new Client();

        customAddress.city = city;
        client.customAddress = customAddress;


    }
}
