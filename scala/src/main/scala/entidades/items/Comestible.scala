package entidades.items

import entidades.participantes.{Individuo, Vikingo}

class Comestible(hambreDisminuible: Double) extends Item {

  def comer(vikingo: Vikingo): Vikingo = vikingo.disminuirHambre(hambreDisminuible)

}