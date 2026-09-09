package domain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.time.LocalDate;

enum OrderStatus {
	CREATED, PAID, SHIPED, CANCELLED
}

enum ProductCategory{
	ELETRONICS, CLOTHING, BOOKS, FOOD, ACCESSORIES
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
		List<OrderItem> itemList) {
	
	BigDecimal totalOrderPrice() {
		return itemList.stream()
				.map(i-> i.unitPrice().multiply(BigDecimal.valueOf(i.quantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}

record OrderDTO(
		Long id,
		String clientEmail,
		LocalDate date,
		OrderStatus status) {
	
static OrderDTO fromEntity(Order order) {
		return new OrderDTO(order.id(), order.clientEmail(), order.date(), order.status());
	}

@Override
	public final String toString() {
		return "Id: " +id() + " - " + date() + " -> " + clientEmail() + " - " + status();
	}
}


		
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
			    )),
			    new Order(6L, "sergio@email.com", LocalDate.of(2026, 9, 1), OrderStatus.SHIPED, List.of(
				new OrderItem("Ipad", ProductCategory.ELETRONICS, 1, new BigDecimal("500.00")),
				new OrderItem("Capa Ipad", ProductCategory.ACCESSORIES, 1, new BigDecimal("50.00"))
				))
			);
		
	
		
		
		/* O setor de comunicação precisa enviar um e-mail de confirmação para os clientes que efetuaram pagamentos. 
		 * Escreva um método que receba a List<Pedido> e retorne uma lista de String contendo apenas os e-mails 
		 * únicos de clientes cujos pedidos estão com o status PAGO.
		 */
		
		sendEmail(filterPaidOrders(orders));
		
		/*
		 * O dashboard precisa exibir os 2 pedidos mais recentes que foram enviados. Crie um DTO 
		 * (pode ser um record PedidoResumoDTO(Long id, String clienteEmail, LocalDate data)) e monte uma 
		 * pipeline que filtre por pedidos enviados, ordene pela data de forma decrescente, limite a 2 registros e 
		 * mapeie para este DTO.
		 */
		
		printDTO(ordersShippedDTO(orders));
		
		/*
		 * A equipe de logística precisa saber quais produtos físicos foram efetivamente comercializados. Escreva uma solução que navegue
		 *  pela lista de pedidos, selecione apenas os pedidos PAGO ou ENVIADO, acesse a lista interna de itens de cada pedido e extraia 
		 *  um Set<String> contendo o nome de todos os produtos da categoria ELETRONICOS vendidos.
		 */
		
		printProducts(getEletronicProductsSale(orders));
		
		/*
		 * O departamento financeiro precisa consolidar o faturamento da empresa. Crie uma consulta que agrupe os pedidos pelo seu 
		 * StatusPedido e calcule o valor total faturado acumulado em cada status. (Dica: lembre-se do cálculo de quantidade X preço 
		 * unitário usando BigDecimal).
		 */
		
		printReport(financialReport(orders));
			
	}
	
	private static Map<String, BigDecimal> financialReport(List<Order> orders) {
		return orders.stream()
				.findFirst()
				.map(o-> Map.of(o.clientEmail(), o.totalOrderPrice()))
				.orElse(Map.of());
	}

	private static Set<String> getEletronicProductsSale(List<Order> orders) {
		return orders.stream()
				.filter(s-> s.status() == OrderStatus.PAID || s.status() == OrderStatus.SHIPED) // Executa o filtro no Pedido
				.flatMap(o-> o.itemList().stream()) //Cria um stream para os items do pedido
				.filter(c-> c.category() == ProductCategory.ELETRONICS) // Filtra os itens do pedido
				.map(OrderItem::product) // Seleciona apenas o atributo String produto
				.collect(Collectors.toSet());
		
	}

	private static void printDTO(List<OrderDTO> dtoList) {
		dtoList.forEach(System.out::println);
	}
	
	private static void printProducts(Set<String> liStrings) {
		liStrings.forEach(item -> System.out.printf("%s ",item));
	}
	private static void printReport(Map<String, BigDecimal> mapReport) {
		mapReport.forEach((keyString, bigDecimalValue) -> System.out.printf("%s R$: %.2f\n", keyString, bigDecimalValue));
	}
	
	private static List<OrderDTO> ordersShippedDTO(List<Order> orders) {
		return orders.stream()
				.filter(o-> o.status() == OrderStatus.SHIPED) // filtra pelo status do pedido usando
				.sorted(Comparator.comparing(Order::date).reversed())
				.limit(2)
				.map(OrderDTO::fromEntity)
				//.map(o-> OrderDTO.fromEntity(o))
				.toList();
	}

	private static void sendEmail(List<String> list) {
		for(String s:list) {
			System.out.println("Sending email to: " + s);
		}
	}
	
	private static List<String> filterPaidOrders(List<Order> ordersList){
				return ordersList.stream()
				.filter(p-> p.status() == OrderStatus.PAID)
				.map(Order::clientEmail)
				.distinct()
				.toList();
	}
	
	
}