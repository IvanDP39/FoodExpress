package foodexpress

enum class TipoCliente(val descuento: Double){
    regular(0.05),
    vip(0.10),
    prenium(0.15)
}