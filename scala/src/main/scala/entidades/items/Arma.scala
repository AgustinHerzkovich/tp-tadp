package entidades.items

class Arma(nombre: String, danio: Double) extends Item {
  override def danioItem: Double = danio
}