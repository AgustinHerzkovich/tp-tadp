  package entidades.torneo.postas

  import entidades.competidores.Competidor
  import entidades.requisitos.obj.NoRequisito
  import entidades.requisitos.{Requisito, RequisitoMontura}

  case class Carrera(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito) extends Posta{
    require(requisitoDeParticipacion().isInstanceOf[RequisitoMontura] || requisitoDeParticipacion() == NoRequisito)
  }
