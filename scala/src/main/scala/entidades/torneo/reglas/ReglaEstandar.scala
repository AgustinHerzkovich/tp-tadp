package entidades.torneo.reglas

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon

class ReglaEstandar extends Regla  {
  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)

  def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.head
  
  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones

  //def prepararVikingos(participantes: List[Participante]): List[Vikingo] = participantes 
}