package entidades.requisitos

import entidades.participantes.Individuo

trait Requisito[T <: Individuo] {

  def apply(individuo: T) : Boolean
}