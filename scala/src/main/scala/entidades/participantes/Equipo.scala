package entidades.participantes

class Equipo(vikingos: => List[Vikingo], val nombre: String) extends Participante {
  lazy val miembros: List[Vikingo] = vikingos
}