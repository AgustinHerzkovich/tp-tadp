package entidades.requisitos

import entidades.competidores.Competidor

class RequisitoBarbarosidad (barbarosidadMinima: Double) extends Requisito {

  override def cumple(competidor: Competidor): Boolean = competidor.barbarosidad >= barbarosidadMinima

}
