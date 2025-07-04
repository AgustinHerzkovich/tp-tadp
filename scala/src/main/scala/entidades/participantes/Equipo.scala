package entidades.participantes

class Equipo(vikingos: => List[Vikingo], val nombre: String) extends Participante {
  def miembros: List[Vikingo] = vikingos
}