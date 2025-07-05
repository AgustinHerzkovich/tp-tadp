package entidades.requisitos

import entidades.participantes.Vikingo

class RequisitoPesoMaximo(pesoMaximoDelVikingo: Double) extends Requisito[Vikingo]{

  def apply(vikingo: Vikingo): Boolean =  vikingo.peso <= pesoMaximoDelVikingo
}