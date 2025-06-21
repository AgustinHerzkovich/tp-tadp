package entidades.torneo.postas

import entidades.requisitos.RequisitoCargaMinima
import entidades.competidores.Competidor

case class Pesca(override val hambreQueGenera: Double, val preRequisito: Option[RequisitoCargaMinima]) extends Posta(hambreQueGenera){
  override def cumplePre(competidor: Competidor): Boolean = {
    preRequisito.forall(_.cumple(competidor))
  }
}
