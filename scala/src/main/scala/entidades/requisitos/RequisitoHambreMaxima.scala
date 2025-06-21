package entidades.requisitos

import entidades.competidores.Competidor

class RequisitoHambreMaxima(val hambreMaxima: Double) extends Requisito{
  override def cumple(competidor: Competidor): Boolean = competidor.porcentajeHambre <= hambreMaxima
  // competidor.tieneHambreSuficiente
}
