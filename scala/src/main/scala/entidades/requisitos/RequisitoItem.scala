package entidades.requisitos

import entidades.competidores.Competidor
import entidades.items.Item

class RequisitoItem(itemRequerido : Item) extends Requisito {

  override def cumple(competidor: Competidor): Boolean = competidor.item == itemRequerido
  
}
