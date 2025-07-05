package entidades.requisitos

import entidades.participantes.Individuo

class RequisitoBarbarosidad (barbarosidadMinima: Double) extends Requisito[Individuo] {

  def apply(individuo: Individuo): Boolean = individuo.barbarosidad >= barbarosidadMinima
}