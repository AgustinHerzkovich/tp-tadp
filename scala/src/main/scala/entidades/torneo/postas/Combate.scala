package entidades.torneo.postas

import entidades.requisitos.{Requisito, RequisitoBarbarosidad, RequisitoItem}

case class Combate(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito) extends Posta {

  require(requisitoDeParticipacion.isInstanceOf[RequisitoBarbarosidad] || requisitoDeParticipacion.isInstanceOf[RequisitoItem])
}