package entidades.requisitos

import entidades.participantes.Individuo

class RequisitoDanio(danioMaximo : Double) extends Requisito{

  override def apply(individuo: Individuo): Boolean = individuo.danio <= danioMaximo
}