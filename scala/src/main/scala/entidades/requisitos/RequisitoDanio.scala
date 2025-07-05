package entidades.requisitos

import entidades.participantes.Vikingo

class RequisitoDanio(danioMaximo : Double) extends Requisito[Vikingo]{

  def apply(vikingo: Vikingo): Boolean = vikingo.danio <= danioMaximo
}