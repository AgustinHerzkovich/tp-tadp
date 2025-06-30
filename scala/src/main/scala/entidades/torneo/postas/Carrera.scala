  package entidades.torneo.postas

  import entidades.participantes.Individuo
  import entidades.requisitos.obj.NoRequisito
  import entidades.requisitos.{Requisito, RequisitoMontura}

  case class Carrera(hambre: Double, requisito: Requisito) extends Posta{
    override def hambreQueGenera(): Double = hambre
    override def requisitoDeParticipacion(): Requisito = requisito

    require(requisitoDeParticipacion().isInstanceOf[RequisitoMontura] || requisitoDeParticipacion() == NoRequisito)
  }
