package entidades.competidores

import entidades.dragones.Dragon
import entidades.items.Item

//JINETE//
 case class Jinete(vikingo: Vikingo, dragon: Dragon) extends Competidor {

  require(dragon.puedeSerMontado(vikingo))

  override def peso: Double = dragon.peso + vikingo.peso

  override def barbarosidad: Double = vikingo.barbarosidad

  override def item: Option[Item] = vikingo.item

  override def danio: Double = vikingo.danio + dragon.danio
  
  override def velocidad: Double = dragon.velocidad - vikingo.peso

  override def cargaMaxima: Double = dragon.cargaMaxima - vikingo.peso

  override def aumentarHambre(porcentaje: Double): Jinete = this.copy(vikingo = vikingo.aumentarHambre(porcentaje))

  override def porcentajeHambre: Double = vikingo.porcentajeHambre

  override def disminuirHambre(porcentaje: Double): Jinete = this.copy(vikingo = vikingo.disminuirHambre(porcentaje))
  
  override def puedeSeguir(): Boolean = vikingo.puedeSeguir()

}


