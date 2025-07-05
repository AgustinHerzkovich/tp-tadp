package entidades.requisitos

import entidades.participantes.Individuo
import entidades.items.Item

class RequisitoItem[T <: Individuo](condicion: Item => Boolean) extends Requisito[T] {

  def apply(individuo: T): Boolean = individuo.item.exists(condicion)
}