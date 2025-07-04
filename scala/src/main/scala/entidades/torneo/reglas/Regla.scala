package entidades.torneo.reglas

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon

trait Regla {
  def aprobados(vikingos: List[Vikingo]): List[Vikingo] = ordenDeMonturas(quienesPasanDeRonda(vikingos))
  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos
  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)
  def quienGana(participantes: List[_ <: Participante]): Participante // Upper type bound para poder llamarlo con List[Vikingo] en Torneo
  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones
  def quienesParticipan(participantes : List[Participante]) : List[Vikingo]
}