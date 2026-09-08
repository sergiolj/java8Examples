package domain;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

enum OrderStatus {
	CREATED, PAID, SHIPED, CANCELLED
}

enum ProductCategory{
	ELETRONICS, CLOTHING, BOOKS, FOOD
}

record OrderItem(
		String product,
		ProductCategory category,
		int quantity,
		BigDecimal unitPrice) {}

record Order(
		Long id,
		String clientEmail,
		LocalDate date,
		OrderStatus status,
		List<OrderItem> itemList) {}


		
public class FilterAndMappingStreams {
	public static void main(String[] args) {
		List<Order> orders = List.of(
			    new Order(1L, "ana@email.com", LocalDate.of(2026, 8, 10), OrderStatus.PAID, List.of(
			        new OrderItem("Notebook", ProductCategory.ELETRONICS, 1, new BigDecimal("3500.00")),
			        new OrderItem("Mouse", ProductCategory.ELETRONICS, 2, new BigDecimal("80.00"))
			    )),
			    new Order(2L, "bruno@email.com", LocalDate.of(2026, 8, 12), OrderStatus.SHIPED, List.of(
			        new OrderItem("Camiseta", ProductCategory.CLOTHING, 3, new BigDecimal("50.00")),
			        new OrderItem("Livro Java", ProductCategory.BOOKS, 1, new BigDecimal("120.00"))
			    )),
			    new Order(3L, "ana@email.com", LocalDate.of(2026, 8, 15), OrderStatus.PAID, List.of(
			        new OrderItem("Teclado", ProductCategory.ELETRONICS, 1, new BigDecimal("200.00"))
			    )),
			    new Order(4L, "carla@email.com", LocalDate.of(2026, 8, 18), OrderStatus.CANCELLED, List.of(
			        new OrderItem("Monitor", ProductCategory.ELETRONICS, 1, new BigDecimal("1200.00"))
			    )),
			    new Order(5L, "bruno@email.com", LocalDate.of(2026, 8, 20), OrderStatus.PAID, List.of(
			        new OrderItem("Calça Jeans", ProductCategory.CLOTHING, 1, new BigDecimal("150.00")),
			        new OrderItem("Tênis", ProductCategory.CLOTHING, 1, new BigDecimal("300.00"))
			    ))
			);
		
		/* O setor de comunicação precisa enviar um e-mail de confirmação para os clientes que efetuaram pagamentos. 
		 * Escreva um método que receba a List<Pedido> e retorne uma lista de String contendo apenas os e-mails únicos de clientes 
		 * cujos pedidos estão com o status PAGO.
		 */
		
		sendEmail(filterPaidOrders(orders));
			
	}
	
	static void sendEmail(List<String> list) {
		for(String s:list) {
			System.out.println("Sending email to: " + s);
		}
	}
	
	static List<String> filterPaidOrders(List<Order> ordersList){
				return ordersList.stream()
				.filter(p-> p.status() == OrderStatus.PAID)
				.map(Order::clientEmail)
				.distinct()
				.toList();
	}
	
}