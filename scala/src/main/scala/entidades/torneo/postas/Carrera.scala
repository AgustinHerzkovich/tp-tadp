  package entidades.torneo.postas
  
  import entidades.requisitos.obj.{NoRequisito, RequisitoMontura}
  import entidades.requisitos.Requisito

  case class Carrera(override val hambreQueGenera: Double, override val requisitoDeParticipacion: Requisito) extends Posta{
    require(requisitoDeParticipacion == RequisitoMontura || requisitoDeParticipacion == NoRequisito)
  }