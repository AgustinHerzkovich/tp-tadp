package entidades.requisitos

import entidades.participantes.Individuo

class RequisitoCargaMinima(cargaMinima : Double) extends Requisito[Individuo] {

  def apply(individuo: Individuo): Boolean = individuo.cargaMaxima >= cargaMinima
}