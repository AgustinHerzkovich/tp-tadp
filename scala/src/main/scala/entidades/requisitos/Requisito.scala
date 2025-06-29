package entidades.requisitos

import entidades.competidores.Competidor

trait Requisito {

  def apply(competidor: Competidor) : Boolean

}

