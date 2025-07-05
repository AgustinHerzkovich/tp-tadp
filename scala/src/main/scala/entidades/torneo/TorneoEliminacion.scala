package entidades.torneo

import entidades.dragones.Dragon
import entidades.participantes.Vikingo
import entidades.postas.Posta

class TorneoEliminacion(postas: List[Posta], dragones: List[Dragon], val cantidadQuePasa: Integer) extends TorneoEstandar(postas, dragones) {
  override protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(cantidadQuePasa)
}