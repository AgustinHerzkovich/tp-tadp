package entidades.torneo.reglas

import entidades.participantes.Vikingo

class ReglaTorneoInverso extends ReglaEstandar {
  override protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.drop(vikingos.length / 2)

  override def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.lastOption.getOrElse(throw new Exception("No hay ganador")) // TODO: revisar esto
}