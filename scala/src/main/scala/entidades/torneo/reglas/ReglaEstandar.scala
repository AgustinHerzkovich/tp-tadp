package entidades.torneo.reglas

import entidades.participantes.Vikingo
import entidades.dragones.Dragon

class ReglaEstandar extends Regla  {
  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)

  def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.headOption.getOrElse(throw new NoSuchElementException("No hay ganador")) // TODO: revisar esto

  def quienesParticipan(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones
}