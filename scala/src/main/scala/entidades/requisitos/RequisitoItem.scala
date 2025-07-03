package entidades.requisitos

import entidades.participantes.Individuo
import entidades.items.Item

class RequisitoItem(condicion: Item => Boolean) extends Requisito {

  override def apply(individuo: Individuo): Boolean = individuo.item.exists(condicion)
}