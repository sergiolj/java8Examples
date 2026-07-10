import java.util.*;
import java.util.stream.Collectors;

class Product{
    private String name;
    private String brand;
    double price;

    public Product(String name, String brand){
        this(name,brand,0);

    }

    public Product(String name, String brand, double price){
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String getBrand(){
        return brand;
    }

    public String getName() {
        return name;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
}

class Employee {
    private String name;
    private String department;

    public Employee(String name, String department){
        this.name = name;
        this.department = department;
    }

    public String getDepartment(){
        return department;
    }

    @Override
    public String toString() {
        return name;
    }
}


public class StreamExercises {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<Product> stock = Arrays.asList(
                new Product("Galaxy S23", "Samsung"),
                new Product("iPhone 14", "Apple"),
                new Product("iPhone 15", "Apple"),
                new Product("Mac Mini", "Apple"),
                new Product("iPad", "Apple"),
                new Product("Pixel 7", "Google")
        );
        String brand = "Apple";

        filterWithoutStream(numbers);
        filterUsingStream(numbers);
        booleanFilterWithoutStream(stock, brand);
        listFilterUsingStream(stock, brand);

        System.out.println("\nUsando verificador boolean anyMatch() da interface Stream.");

        System.out.println("Existem produtos no estoque: " + booleanFilterUsingStream(stock, brand));

        List<Double> prices = Arrays.asList(4500.0, 4300.0, 6700.0, 12000.0, 5000.0, 3800.0);
        for(int i=0; i<prices.size(); i++){
            stock.get(i).setPrice(prices.get(i));
        }

        List<Product> shoppingChart = new ArrayList<>();
        shoppingChart.add(stock.get(1));
        shoppingChart.add(stock.get(2));
        shoppingChart.add(stock.get(1));


        System.out.println("\nUsando Enhanced For o total de uma lista.");
        sumOfShoppingCartWithoutStream(shoppingChart);

        System.out.println("\nUsando mapToDouble para calcular o total de uma lista.");
        sumOfShoppingCartUsingStream(shoppingChart);

        List<Employee> company = Arrays.asList(
                new Employee("Alice", "IT"),
                new Employee("Bob", "HR"),
                new Employee("Carlos", "IT"),
                new Employee("Diana", "SALES")
        );
        System.out.println("\nCriar listas agrupadas por departamento usando lista Map e o Enhanced For.");
        creatingMapFromList(company);

        System.out.println("\nCriar listas agrupadas por departamento Stream.");
        creatingMapFromListUsingStream(company);


    }

    private static void creatingMapFromListUsingStream(List<Employee> company) {
        Map<String, List<Employee>> employersByDepartment = company.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(employersByDepartment);
    }

    private static void creatingMapFromList(List<Employee> company) {
        Map<String, List<Employee>> employersByDepartment = new HashMap<>();
        for(Employee e : company){
            String department = e.getDepartment();
            if(!employersByDepartment.containsKey(department)){
                employersByDepartment.put(department, new ArrayList<>());
            }
            employersByDepartment.get(department).add(e);
        }
        System.out.println(employersByDepartment);
    }


    private static void sumOfShoppingCartWithoutStream(List<Product> shoppingChart) {
        double total = 0;
        for(Product p : shoppingChart){
            total += p.getPrice();
        }
        System.out.println("Valor total dos produtos R$: " + total);
    }

    private static void sumOfShoppingCartUsingStream(List<Product> shoppingChart) {
        double total = 0;
        total = shoppingChart.stream().mapToDouble(Product::getPrice).sum();
        System.out.println("Valor total dos produtos R$: " + total);
    }

    private static void filterUsingStream(List<Integer> listInteger) {
        List<Integer> pars = listInteger.stream()
                .filter(n -> n%2==0)
                .toList();

        System.out.println("Numbers: " + listInteger);
        System.out.println(("Par numbers only: " + pars));
    }

    private static void filterWithoutStream(List<Integer> listInteger) {

        List<Integer> pars = new ArrayList<>();
        for(Integer n : listInteger){
            if(n%2==0){
                pars.add(n);
            }
        }
        System.out.println("Numbers: " + listInteger);
        System.out.println(("Par numbers only: " + pars));
    }

    private static void booleanFilterWithoutStream(List<Product> productList, String brand){
        boolean existsAnyAppleProduct = false;
        for(Product p : productList){
            if(p.getBrand().equalsIgnoreCase(brand)){
                existsAnyAppleProduct = true;
                break;
            }
        }
        if(existsAnyAppleProduct){
            List<String> products = new ArrayList<>();
            for(Product p : productList){
                if(p.getBrand().equalsIgnoreCase(brand)) products.add(p.getName());
            }
            System.out.println("\nEncontrado produto da marca: " + brand);
            System.out.println("Produtos disponíveis: " + products);
        }
    }

    private static void listFilterUsingStream(List<Product> productList, String brand){
        List<String> products = productList.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .map(Product::getName)
                .toList();

        if(!products.isEmpty()){
            System.out.println("\nEncontrado produto da marca: " + brand);
            System.out.println("Produtos disponíveis: " + products);
        }
    }

    private static boolean booleanFilterUsingStream(List<Product> productList, String brand) {
        return productList.stream()
                .anyMatch(p -> p.getBrand().equalsIgnoreCase(brand));
    }
}
