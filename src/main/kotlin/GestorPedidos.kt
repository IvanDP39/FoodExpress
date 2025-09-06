
package foodexpress

class GestorPedidos {

    private val Pedido = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto){
        Pedido.add(producto)
    }

    fun calcularTotal(): Double {
        return Pedido.sumOf {it.precio}
    }

    fun mostrarPedido() {
        println("=== RESUMEN DEL PEDIDO ===")
        for ((index, producto) in Pedido.withIndex()){
            println("${index }. ${producto.nombre} - \$${producto.precio}")
        }
    }
}