package entidades.torneo.postas

import entidades.requisitos.{Requisito, RequisitoCargaMinima}
import entidades.competidores.Competidor
import entidades.requisitos.obj.NoRequisito

case class Pesca(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito) extends Posta(){
  require(requisitoDeParticipacion().isInstanceOf[RequisitoCargaMinima] || requisitoDeParticipacion() == NoRequisito)
}
