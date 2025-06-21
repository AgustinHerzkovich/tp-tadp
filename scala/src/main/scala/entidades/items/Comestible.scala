package entidades.items

import entidades.competidores.{Competidor, Vikingo}

class Comestible(hambreDisminuible: Double) extends Item {

  def comer(vikingo: Vikingo): Vikingo = vikingo.disminuirHambre(hambreDisminuible)

}