package entidades.requisitos

import entidades.competidores.{Competidor, Jinete}

class RequisitoMontura extends Requisito{

  override def cumple(competidor: Competidor) = competidor match {
      case _:Jinete => true
      case _ => false
  }

}
