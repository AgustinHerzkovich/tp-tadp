package entidades.items

class Arma(override val nombre: String, danio: Double) extends Item {
  override def danioItem: Double = danio
}