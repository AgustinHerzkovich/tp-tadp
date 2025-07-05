package entidades.torneo

import entidades.dragones.Dragon
import entidades.participantes.Vikingo
import entidades.postas.Posta

class TorneoHandicap(postas: List[Posta], dragones: List[Dragon]) extends TorneoEstandar(postas, dragones) {
  override protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos.reverse
}