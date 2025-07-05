package entidades.torneo

import entidades.dragones.Dragon
import entidades.participantes.Vikingo
import entidades.postas.Posta

class TorneoInverso(postas: List[Posta], dragones: List[Dragon]) extends TorneoEstandar(postas, dragones) {
  override protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.drop(vikingos.length / 2)

  override protected def quienGana(vikingos: List[Vikingo]): Option[Vikingo] = vikingos.lastOption
}