package foodexpress
import kotlinx.coroutines.*


class GestorPedidos(private val tipoCliente: TipoCliente) {

    private val pedido = mutableListOf<PedidoItem>()

    // Procesar selección del producto desde el Main
    fun procesarProductoSeleccionado(producto: Producto) {
        when (producto) {
            is ProductoPrincipal -> {
                print("¿Quieres la versión premium de ${producto.nombre}? (s/n): ")
                val respuesta = readln()
                val productoFinal = if (respuesta.equals("s", ignoreCase = true)) {
                    ProductoPrincipal(
                        producto.nombre,
                        producto.precio * 1.5,
                        true
                    )
                } else {
                    producto
                }
                agregarProducto(productoFinal)
                println("Agregado: ${productoFinal.nombre} ${if (productoFinal.premium) "(Premium)" else ""}")
            }

            is ProductoBebida -> {
                print("Elige el tamaño para ${producto.nombre} (pequeño/mediano/grande): ")
                val tamanoElegido = readln().lowercase()
                val precioFinal = when (tamanoElegido) {
                    "pequeño" -> producto.precio
                    "mediano" -> producto.precio * 1.2
                    "grande" -> producto.precio * 1.5
                    else -> producto.precio
                }
                val bebidaFinal = ProductoBebida(
                    producto.nombre,
                    precioFinal,
                    tamanoElegido
                )
                agregarProducto(bebidaFinal)
                println("Agregando: ${bebidaFinal.nombre}(${bebidaFinal.tamano})")
            }
        }
    }

    // Agrega el producto a la lista de pedidos
    private fun agregarProducto(producto: Producto) {
        pedido.add(PedidoItem(producto, EstadoPedido.RECIBIDO))
    }

    // Calcula total con descuento e IVA
    fun calcularTotal(): Double {
        val subtotal = pedido.sumOf { it.producto.precio }
        val descuento = subtotal * tipoCliente.descuento
        val totalConDescuento = subtotal - descuento
        val impuesto = totalConDescuento * 0.19
        return totalConDescuento + impuesto
    }

    // Muestra el resumen del pedido
    fun mostrarPedido() {
        println("=== RESUMEN DEL PEDIDO ===")
        for ((index, item) in pedido.withIndex()) {
            val producto = item.producto
            val estado = item.estado
            val detalle = when (producto) {
                is ProductoPrincipal -> if (producto.premium) "(Premium)" else ""
                is ProductoBebida -> "(${producto.tamano})"
                else -> ""
            }
            println("${index + 1}. ${producto.nombre} $detalle - \$${"%.2f".format(producto.precio)} | Estado: $estado")
        }

        val subtotal = pedido.sumOf { it.producto.precio }
        val descuento = subtotal * tipoCliente.descuento
        val totalConDescuento = subtotal - descuento
        val impuesto = totalConDescuento * 0.19
        val totalFinal = totalConDescuento + impuesto

        println("\nCliente: ${tipoCliente.name}, Descuento: ${tipoCliente.descuento * 100}%")
        println("Subtotal: \$${"%.2f".format(subtotal)}")
        println("Descuento: -\$${"%.2f".format(descuento)}")
        println("IVA (19%): \$${"%.2f".format(impuesto)}")
        println("Total final: \$${"%.2f".format(totalFinal)}")
    }

    // Simula la preparación de cada pedido de manera asíncrona
    suspend fun simularPreparacion() = coroutineScope {
        for (i in pedido.indices) {
            launch {
                println("Pedido ${pedido[i].producto.nombre}: RECIBIDO")
                delay(2000)
                pedido[i].estado = EstadoPedido.EN_PREPARACION
                println("Pedido ${pedido[i].producto.nombre}: EN PREPARACION")
                delay(3000)
                pedido[i].estado = EstadoPedido.LISTO
                println("Pedido ${pedido[i].producto.nombre}: LISTO")
                delay(1000)
                pedido[i].estado = EstadoPedido.ENTREGADO
                println("Pedido ${pedido[i].producto.nombre}: ENTREGADO")
            }
        }
    }
}
