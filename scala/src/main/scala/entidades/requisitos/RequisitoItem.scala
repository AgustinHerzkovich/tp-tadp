package entidades.requisitos

import entidades.participantes.Individuo
import entidades.items.Item

class RequisitoItem(itemRequerido : Item) extends Requisito {

  override def apply(individuo: Individuo): Boolean = individuo.item == itemRequerido
  
}
