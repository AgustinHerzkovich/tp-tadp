package entidades.requisitos.obj

import entidades.participantes.{Individuo, Jinete}
import entidades.requisitos.Requisito

object RequisitoMontura extends Requisito[Individuo]{

  def apply(individuo: Individuo): Boolean = individuo match {
      case _: Jinete => true
      case _ => false
  }
}