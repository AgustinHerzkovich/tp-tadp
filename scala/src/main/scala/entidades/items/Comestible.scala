package entidades.items

import entidades.participantes.Vikingo

class Comestible(override val nombre: String, hambreDisminuible: Double) extends Item {
  def comer(vikingo: Vikingo): Vikingo = vikingo.disminuirHambre(hambreDisminuible)
}