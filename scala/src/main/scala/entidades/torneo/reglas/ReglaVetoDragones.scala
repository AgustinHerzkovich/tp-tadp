package entidades.torneo.reglas

import entidades.dragones.Dragon

class ReglaVetoDragones(val condicion: Dragon => Boolean) extends ReglaEstandar {
  override def dragonesDisponibles(dragones : List[Dragon]): List[Dragon] = dragones.filter(dragon => condicion(dragon))
}
