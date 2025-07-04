package entidades.torneo.reglas

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon

trait Regla {
  def aprobados(vikingos: List[Vikingo]): List[Vikingo] = ordenDeMonturas(quienesPasanDeRonda(vikingos))
  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos
  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)
  def quienGana(vikingos: List[Vikingo]): Participante
  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones
  def quienesParticipan(participantes : List[Participante]) : List[Vikingo] // "Filtra" los participantes y los deja como List[Vikingo]
}