package entidades.requisitos

import entidades.participantes.Individuo

trait Requisito {

  def apply(individuo: Individuo) : Boolean
}