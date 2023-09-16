package com.utn.tpPersistenciaJpa;

import com.utn.tpPersistenciaJpa.entidades.*;
import com.utn.tpPersistenciaJpa.enumeraciones.Estado;
import com.utn.tpPersistenciaJpa.enumeraciones.FormaPago;
import com.utn.tpPersistenciaJpa.enumeraciones.Tipo;
import com.utn.tpPersistenciaJpa.enumeraciones.TipoEnvio;
import com.utn.tpPersistenciaJpa.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TpPersistenciaJpaApplication {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	RubroRepository rubroRepository;

	public static void main(String[] args) {

		SpringApplication.run(TpPersistenciaJpaApplication.class, args);

		System.out.println("------------> ESTOY FUNCIONANDO <-----------");
	}

	@Bean
	CommandLineRunner init(ClienteRepository clienteRepository, RubroRepository rubroRepository) {
		return args -> {
			System.out.println("------------> ESTOY FUNCIONANDO <-----------");

			//CREAMOS RUBROS

			Rubro rubro1 = Rubro.builder()
					.denominacion("Pizza Especial")
					.build();
			Rubro rubro2 = Rubro.builder()
					.denominacion("Papas Fritas")
					.build();

			//CREAMOS PRODUCTOS

			Producto producto1 = Producto.builder()
					.precioCompra(800)
					.precioVenta(1000)
					.stockActual(15)
					.stockMinimo(4)
					.tiempoEstimadoCocina(10)
					.tipo(Tipo.insumo)
					.denominacion("Vegetales")
					.receta("Cortados en cuadritos")
					.unidadMedida("kg")
					.build();

			Producto producto2 = Producto.builder()
					.precioCompra(200)
					.precioVenta(500)
					.stockActual(10)
					.stockMinimo(3)
					.tiempoEstimadoCocina(15)
					.tipo(Tipo.manufacturado)
					.denominacion("Carnes")
					.receta("Cocinada en plancha")
					.unidadMedida("kg")
					.build();

			Producto producto3 = Producto.builder()
					.precioCompra(900)
					.precioVenta(800)
					.stockActual(18)
					.stockMinimo(10)
					.tiempoEstimadoCocina(20)
					.tipo(Tipo.manufacturado)
					.denominacion("Panes")
					.receta("Precalentados en horno")
					.unidadMedida("kg")
					.build();

			rubro1.agregarProducto(producto2);
			rubro2.agregarProducto(producto1);
			rubro1.agregarProducto(producto3);

			//GUARDAMOS LOS RUBROS

			rubroRepository.save(rubro1);
			rubroRepository.save(rubro2);

			//CREAMOS CLIENTES

			Cliente cliente1 = Cliente.builder()
					.nombre("Roman")
					.apellido("Riquelme")
					.telefono(261215698)
					.email("gelasuga@gmail.com")
					.build();

			Cliente cliente2 = Cliente.builder()
					.nombre("Leonel")
					.apellido("Messi")
					.telefono(261156895)
					.email("ritmo36@gmail.com")
					.build();

			//CREAMOS DOMICILIOS

			Domicilio domicilio1 = Domicilio.builder()
					.calle("Av. San Martin")
					.numero(492)
					.localidad("Mendoza, Ciudad")
					.build();

			Domicilio domicilio2 = Domicilio.builder()
					.calle("Las rabonas")
					.numero(157)
					.localidad("Mendoza, Las Heras")
					.build();

			Domicilio domicilio3 = Domicilio.builder()
					.calle("Soldado desconocdio")
					.numero(633)
					.localidad("Mendoza, Junin")
					.build();

			//AGREGAMOS DOMICILIOS A CLIENTE

			cliente1.agregarDomicilio(domicilio1);
			cliente1.agregarDomicilio(domicilio3);
			cliente2.agregarDomicilio(domicilio2);

			//CREAMOS DETALLEPEDIDO

			DetallePedido detallePedido1 = DetallePedido.builder()
					.cantidad(120)
					.subtotal(938.15)
					.build();

			DetallePedido detallePedido2 = DetallePedido.builder()
					.cantidad(300)
					.subtotal(500.15)
					.build();

			DetallePedido detallePedido3 = DetallePedido.builder()
					.cantidad(190)
					.subtotal(696.45)
					.build();

			//CREAR PEDIDOS

			Pedido pedido1 = Pedido.builder()
					.estado(Estado.Entregado)
					.tipoEnvio(TipoEnvio.delivery)
					.total(9000)
					.fecha("22/06")
					.build();

			Pedido pedido2 = Pedido.builder()
					.estado(Estado.Preparacion)
					.tipoEnvio(TipoEnvio.retiro)
					.total(10000)
					.fecha("19/08")
					.build();

			//CREAMOS FACTURAS

			Factura factura1 = Factura.builder()
					.numero(20)
					.descuento(0.12)
					.formaPago(FormaPago.efectivo)
					.totalFactura(6000)
					.fecha("17/05")
					.build();

			Factura factura2 = Factura.builder()
					.numero(21)
					.descuento(0.19)
					.formaPago(FormaPago.mercadopago)
					.totalFactura(5000)
					.fecha("24/12")
					.build();

			//AGREGAR DETALLEPEDIDO AL PEDIDO

			pedido1.agregarDetalle(detallePedido1);
			pedido2.agregarDetalle(detallePedido2);
			pedido2.agregarDetalle(detallePedido3);

			//AGREGAR PEDIDO AL CLIENTE

			cliente1.agregarPedido(pedido1);
			cliente2.agregarPedido(pedido2);

			//VINCULAR DETALLEPEDIDO CON PRODUCTO

			detallePedido1.setProducto(producto1);
			detallePedido2.setProducto(producto2);
			detallePedido3.setProducto(producto1);

			//VINCULAR FACTURA CON PEDIDO

			pedido1.setFactura(factura1);
			pedido2.setFactura(factura2);

			clienteRepository.save(cliente1);
			clienteRepository.save(cliente2);

			//RECUPERAR EL RUBRO DESDE BD

			Rubro rubroRecuperado = rubroRepository.findById(rubro1.getId()).orElse(null);
			if (rubroRecuperado != null) {
				System.out.println("Denominacion: " + rubroRecuperado.getDenominacion());
				rubroRecuperado.mostrarProductos();
			}

			//RECUPERAR CLIENTE DESDE BD

			Cliente clienteRecuperado = clienteRepository.findById(cliente1.getId()).orElse(null);
			if (clienteRecuperado != null) {
				System.out.println("Nombre: " + clienteRecuperado.getNombre());
				System.out.println("Apellido: " + clienteRecuperado.getApellido());
				System.out.println("Mail: " + clienteRecuperado.getEmail());
				System.out.println("Telefono: " + clienteRecuperado.getTelefono());
				System.out.println("----------------------------------------");
				clienteRecuperado.mostrarDomicilios();
				clienteRecuperado.mostrarPedido();

			}

		};
	}
}