package entidades.requisitos.obj

import entidades.participantes.Individuo
import entidades.requisitos.Requisito

// Void object
object NoRequisito extends Requisito  {
  override def apply(individuo: Individuo) : Boolean = true
}