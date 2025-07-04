package entidades.participantes.obj

import entidades.participantes.Vikingo
import entidades.items.Comestible

object Patapez extends Vikingo(velocidad = 100.0, peso = 100.0, barbarosidad = 100.0, porcentajeHambre = 0.0){
  override val item: Option[Comestible] = Option(Comestible(nombre = "comestible", hambreDisminuible = 5.0)) // overrideo el item para que sea del tipo Option[Comestible]
  
  override def aumentarHambre(porcentaje: Double): Vikingo = super.aumentarHambre(porcentaje*2)

  override def accionLuegoDeParticiparEnPosta(): Vikingo = item.map(_.comer(this)).getOrElse(this)
  
  override def porcentajeHambreMaximo: Double = 50.0
}