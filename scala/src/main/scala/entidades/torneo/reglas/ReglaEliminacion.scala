package entidades.torneo.reglas

import entidades.participantes.Vikingo

class ReglaEliminacion(val cantidadQuePasa: Integer) extends ReglaEstandar {
  override def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(cantidadQuePasa)
}
