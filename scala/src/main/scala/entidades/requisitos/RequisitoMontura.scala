package entidades.requisitos

import entidades.participantes.{Individuo, Jinete}

class RequisitoMontura extends Requisito{

  override def apply(individuo: Individuo) = individuo match {
      case _:Jinete => true
      case _ => false
  }

}
