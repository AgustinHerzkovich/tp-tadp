package entidades.requisitos

import entidades.competidores.Competidor

class RequisitoDanio(danioMaximo : Double) extends Requisito{

  override def apply(competidor: Competidor): Boolean = competidor.danio <= danioMaximo

}
