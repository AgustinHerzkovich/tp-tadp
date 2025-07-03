package entidades.requisitos

import entidades.participantes.Individuo

class RequisitoPesoMaximo(pesoMaximoDelVikingo: Double) extends Requisito{

  override def apply(individuo: Individuo): Boolean =  individuo.peso <= pesoMaximoDelVikingo
}