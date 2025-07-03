  package entidades.torneo.postas
  
  import entidades.requisitos.obj.{NoRequisito, RequisitoMontura}
  import entidades.requisitos.Requisito

  case class Carrera(hambre: Double, requisito: Requisito) extends Posta{
    override def hambreQueGenera(): Double = hambre
    override def requisitoDeParticipacion(): Requisito = requisito

    require(requisitoDeParticipacion() == RequisitoMontura || requisitoDeParticipacion() == NoRequisito)
  }