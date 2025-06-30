package entidades.items

import entidades.participantes.Vikingo

class Comestible(nombre: String, hambreDisminuible: Double) extends Item {
  def comer(vikingo: Vikingo): Vikingo = vikingo.disminuirHambre(hambreDisminuible)
}