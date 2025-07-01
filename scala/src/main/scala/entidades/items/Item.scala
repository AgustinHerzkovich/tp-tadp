package entidades.items

import entidades.participantes.{Individuo, Vikingo}

//abstract class Item {
//TODO: Consultar si podria ser una abstracta
//}

trait Item {
  def danioItem : Double = 0.0
  val nombre: String
}