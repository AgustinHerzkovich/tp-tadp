package entidades.torneo.reglas.obj

import entidades.participantes.Vikingo
import entidades.torneo.reglas.ReglaEstandar

object ReglaHandicap extends ReglaEstandar {
  override protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos.reverse
}