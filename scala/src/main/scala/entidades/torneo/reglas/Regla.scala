package entidades.torneo.reglas

import entidades.participantes.Vikingo
import entidades.dragones.Dragon

trait Regla {
  def aprobados(vikingos: List[Vikingo]): List[Vikingo] = ordenDeMonturas(quienesPasanDeRonda(vikingos))
  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo]
  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo]
  def quienGana(vikingos: List[Vikingo]): Vikingo
  def quienesParticipan(vikingos: List[Vikingo]): List[Vikingo]
  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon]
}
