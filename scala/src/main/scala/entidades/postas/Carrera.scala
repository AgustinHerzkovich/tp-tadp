  package entidades.postas

import entidades.participantes.Individuo
import entidades.requisitos.obj.{NoRequisito, RequisitoMontura}
import entidades.requisitos.Requisito

  case class Carrera(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito[Individuo]) extends Posta{
    require(requisitoDeParticipacion == RequisitoMontura || requisitoDeParticipacion == NoRequisito)
  }