package entidades.torneo.reglas.obj

import entidades.participantes.Vikingo
import entidades.torneo.reglas.ReglaEstandar

object ReglaTorneoInverso extends ReglaEstandar {
  override protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.drop(vikingos.length / 2)

  override def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.last
}