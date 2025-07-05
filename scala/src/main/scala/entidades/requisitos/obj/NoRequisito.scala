package entidades.requisitos.obj

import entidades.participantes.Individuo
import entidades.requisitos.Requisito

// Void object
object NoRequisito extends Requisito[Individuo]  {
  def apply(individuo: Individuo) : Boolean = true
}