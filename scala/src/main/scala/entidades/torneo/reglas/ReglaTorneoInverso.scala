package entidades.torneo.reglas

import entidades.competidores.Vikingo

class ReglaTorneoInverso extends ReglaEstandar {
  override def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.drop(vikingos.length / 2)

  override def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.last
}
