package entidades.requisitos

import entidades.participantes.Individuo

class RequisitoHambreMaxima(val hambreMaxima: Double) extends Requisito{
  override def apply(individuo: Individuo): Boolean = individuo.porcentajeHambre <= hambreMaxima
  // individuo.tieneHambreSuficiente
}
