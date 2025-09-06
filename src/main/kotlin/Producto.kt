package foodexpress
open class Producto(val nombre: String, val precio: Double);


class ProductoPrincipal(
    nombre: String,
    precio: Double,
    val Premium: Boolean
) : Producto(nombre, precio)


class ProductoBebida(
    nombre: String,
    precio: Double,
    val tamano: String
) : Producto(nombre, precio)

