package entidades.requisitos

import entidades.competidores.Competidor

class RequisitoPesoMaximo(pesoMaximoDelVikingo: Double) extends Requisito{

  override def apply(competidor: Competidor): Boolean =  competidor.peso <= pesoMaximoDelVikingo

}
