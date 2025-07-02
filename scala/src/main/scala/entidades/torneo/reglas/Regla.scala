package entidades.torneo.reglas

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon

trait Regla {
  def aprobados(vikingos: List[Vikingo]): List[Vikingo] = ordenDeMonturas(quienesPasanDeRonda(vikingos))
  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo]
  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo]
  def quienGana(vikingos: List[Vikingo]): Vikingo
  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon]
  //def participantesValidos(participantes : List[Participante]) : List[Participante]
  //def prepararVikingos(participantes : List[Participante]) : List[Vikingo]
  //def obtenerParticipantes(vikingos : List[Vikingo]) : List[Participante]
}
