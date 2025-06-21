package entidades.requisitos

import entidades.competidores.Competidor

trait Requisito {

  def cumple(competidor: Competidor) : Boolean

}

