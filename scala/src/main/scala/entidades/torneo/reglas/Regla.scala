package entidades.torneo.reglas

import entidades.competidores.Vikingo
import entidades.dragones.Dragon

trait Regla {
  // def aprobados(vikingos: List[Vikingo]): List[Vikingo]
  def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo]
  def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo]
  def quienGana(vikingos: List[Vikingo]): Vikingo
  def quienesParticipan(vikingos: List[Vikingo]): List[Vikingo]
  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon]
}

/*
dragonesDisponibles
quienesParticipan
ordenDeMonutra
quienesPasanDeRonda
quienGana

*/





