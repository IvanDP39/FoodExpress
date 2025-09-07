package foodexpress
open class Producto(val nombre: String, val precio: Double);


class ProductoPrincipal(
    nombre: String,
    precio: Double,
    val premium: Boolean
) : Producto(nombre, precio)


class ProductoBebida(
    nombre: String,
    precio: Double,
    val tamano: String = "mediano"
) : Producto(nombre, precio)

