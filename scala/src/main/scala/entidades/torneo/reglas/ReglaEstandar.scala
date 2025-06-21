package entidades.torneo.reglas

import entidades.competidores.Vikingo
import entidades.dragones.Dragon

class ReglaEstandar extends Regla  {
  def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)

  def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.head

  def quienesParticipan(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones
}
