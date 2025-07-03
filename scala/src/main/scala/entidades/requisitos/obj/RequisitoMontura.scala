package entidades.requisitos.obj

import entidades.participantes.{Individuo, Jinete}
import entidades.requisitos.Requisito

object RequisitoMontura extends Requisito{

  override def apply(individuo: Individuo) = individuo match {
      case _:Jinete => true
      case _ => false
  }
}