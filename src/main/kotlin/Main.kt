package foodexpress
import kotlinx.coroutines.*

fun main() {
    println("Cliente tipo (regular/vip/premium): ")
    val tipoInput = readln().lowercase()
    val tipoCliente = when(tipoInput){
        "vip" -> TipoCliente.vip
        "premium" -> TipoCliente.prenium
        else -> TipoCliente.regular
    }

    val productos: List<Producto> = listOf(
        ProductoPrincipal("Hamburguesa Clasica", 8990.0, false),
        ProductoPrincipal("Salmon Grillado", 15990.0, false),
        ProductoBebida("Coca Cola", 1990.0),
        ProductoBebida("Jugo Natural", 2990.0)
    )

    println("\nCatalogo: ")
    for ((index, producto) in productos.withIndex()) {
        when(producto){
            is ProductoPrincipal -> println("${index +1}. ProductoPrincipal: ${producto.nombre}, Precio: \$${producto.precio}")
            is ProductoBebida -> println("${index + 1}. ProductoBebida: ${producto.nombre}, Precio base: \$${producto.precio}")
        }
    }

    print("\nIngrese los números de los productos separados por coma (ejemplo: 1,3,4): ")
    val entrada = readln()

    val numerosSeleccionados = entrada.split(",")
        .mapNotNull { it.trim().toIntOrNull() }
        .filter { it in 1..productos.size }

    val gestorPedidos = GestorPedidos(tipoCliente)

    for(num in numerosSeleccionados){
        val productoSeleccionado = productos[num - 1]
        gestorPedidos.procesarProductoSeleccionado(productoSeleccionado)
    }

    gestorPedidos.mostrarPedido()

    println("\n=== Simulación de preparación del pedido ===")
    runBlocking {
        gestorPedidos.simularPreparacion()
    }

    val total = gestorPedidos.calcularTotal()
    println("Total del pedido: \$${"%.2f".format(total)}")
}
