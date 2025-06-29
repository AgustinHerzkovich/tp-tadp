package entidades.requisitos

import entidades.competidores.Competidor

class RequisitoBarbarosidad (barbarosidadMinima: Double) extends Requisito {

  override def apply(competidor: Competidor): Boolean = competidor.barbarosidad >= barbarosidadMinima

}
