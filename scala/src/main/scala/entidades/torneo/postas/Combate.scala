package entidades.torneo.postas

import entidades.participantes.Individuo
import entidades.requisitos.{Requisito, RequisitoBarbarosidad, RequisitoItem}

case class Combate(hambre: Double, requisito: Requisito) extends Posta {
  override def hambreQueGenera(): Double = hambre
  override def requisitoDeParticipacion(): Requisito = requisito

  require(requisitoDeParticipacion().isInstanceOf[RequisitoBarbarosidad] || requisitoDeParticipacion().isInstanceOf[RequisitoItem])
}