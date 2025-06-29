package entidades.requisitos

import entidades.participantes.Individuo

class RequisitoBarbarosidad (barbarosidadMinima: Double) extends Requisito {

  override def apply(individuo: Individuo): Boolean = individuo.barbarosidad >= barbarosidadMinima

}
