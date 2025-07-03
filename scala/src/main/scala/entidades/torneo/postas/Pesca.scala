package entidades.torneo.postas

import entidades.requisitos.{Requisito, RequisitoCargaMinima}
import entidades.requisitos.obj.NoRequisito

case class Pesca(hambre: Double, requisito: Requisito) extends Posta(){
  override def hambreQueGenera(): Double = hambre
  override def requisitoDeParticipacion(): Requisito = requisito

  require(requisitoDeParticipacion().isInstanceOf[RequisitoCargaMinima] || requisitoDeParticipacion() == NoRequisito)
}