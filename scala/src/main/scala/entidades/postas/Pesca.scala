package entidades.postas

import entidades.participantes.Individuo
import entidades.requisitos.{Requisito, RequisitoCargaMinima}
import entidades.requisitos.obj.NoRequisito

case class Pesca(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito[Individuo]) extends Posta(){
  require(requisitoDeParticipacion.isInstanceOf[RequisitoCargaMinima] || requisitoDeParticipacion == NoRequisito)
}