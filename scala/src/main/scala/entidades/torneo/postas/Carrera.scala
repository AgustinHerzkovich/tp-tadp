  package entidades.torneo.postas

  import entidades.competidores.Competidor
  import entidades.requisitos.RequisitoMontura

  case class Carrera(override val hambreQueGenera: Double, val preRequisito: Option[RequisitoMontura]) extends Posta(hambreQueGenera){
    def cumplePre(competidor: Competidor): Boolean = preRequisito.forall(_.cumple(competidor))
  }
