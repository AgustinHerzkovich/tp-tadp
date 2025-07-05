package entidades.postas

import entidades.participantes.Individuo
import entidades.requisitos.{Requisito, RequisitoBarbarosidad, RequisitoItem}

case class Combate(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito[Individuo]) extends Posta {

  require(requisitoDeParticipacion.isInstanceOf[RequisitoBarbarosidad] || requisitoDeParticipacion.isInstanceOf[RequisitoItem[Individuo]])
}