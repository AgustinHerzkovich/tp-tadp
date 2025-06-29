package entidades.requisitos.obj

import entidades.competidores.Competidor
import entidades.requisitos.Requisito

// Void object
object NoRequisito extends Requisito  {
  override def apply(competidor: Competidor) : Boolean = true
}