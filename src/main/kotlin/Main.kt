package foodexpress

fun main() {
    val productos: List<Producto> = listOf(
                ProductoPrincipal("Hambuerguesa Clasica", 8.990, false),
                ProductoPrincipal("Salmon Grillado", 15.990, true),
                ProductoBebida("Coca Cola", 1.990, "mediano" ),
                ProductoBebida("Jugo Natural", 2.990, "grande" )

    )

    println("Catalogo: ")

    for (producto in productos) {
        when (producto) {
            is ProductoPrincipal -> println("ProductoPincipal: ${producto.nombre},Precio: $${producto.precio}, Premium= ${producto.Premium}")
            is ProductoBebida -> println("ProductoBebida: ${producto.nombre}, Precio: $${producto.precio}, Tamano= ${producto.tamano}")
            else -> println("Seleccion no valida")
        }
    }

    print("Ingrese los números de los productos separados por coma (ejemplo: 1,3,4): ")
    val entrada = readln()

    val numerosSeleccionados = entrada.split(",")
        .mapNotNull { it.trim().toIntOrNull() }
        .filter { it in 1..productos.size }

    val gestorPedidos = GestorPedidos()

    for (num in numerosSeleccionados) {
        val productoSeleccionado = productos[num - 1]
        gestorPedidos.agregarProducto(productoSeleccionado)
        println("Agregado: ${productoSeleccionado.nombre}")
    }

    gestorPedidos.mostrarPedido()

    val total = gestorPedidos.calcularTotal()
    println("Total del pedido: \$${"%.2f".format(total)}")

}
