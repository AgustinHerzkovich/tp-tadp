package entidades.torneo.postas

import entidades.competidores.Competidor
import entidades.requisitos.{Requisito, RequisitoBarbarosidad, RequisitoItem}

case class Combate(override val hambreQueGenera: Double, val preRequisito : Either[RequisitoBarbarosidad, RequisitoItem]) extends Posta(hambreQueGenera) {
  override def cumplePre(competidor: Competidor): Boolean = preRequisito.fold(_.cumple(competidor), _.cumple(competidor))
}