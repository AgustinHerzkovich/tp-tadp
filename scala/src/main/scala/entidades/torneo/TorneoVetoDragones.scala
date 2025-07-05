package entidades.torneo

import entidades.dragones.Dragon
import entidades.postas.Posta

class TorneoVetoDragones(postas: List[Posta], dragones: List[Dragon], val condicion: Dragon => Boolean) extends TorneoEstandar(postas, dragones) {
  override protected def dragonesDisponibles(dragones : List[Dragon]): List[Dragon] = dragones.filter(dragon => condicion(dragon))
}