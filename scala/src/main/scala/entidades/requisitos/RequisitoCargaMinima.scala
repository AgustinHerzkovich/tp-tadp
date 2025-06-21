package entidades.requisitos

import entidades.competidores.Competidor

class RequisitoCargaMinima(cargaMinima : Double) extends Requisito {

  override def cumple(competidor: Competidor): Boolean = competidor.cargaMaxima >= cargaMinima

}
