package entidades.participantes.obj

import entidades.participantes.Vikingo
import entidades.items.{Comestible, SistemaDeVuelo}

object Patapez extends Vikingo(velocidad = 100.0, peso = 100.0, barbarosidad = 100.0, porcentajeHambre = 0.0){
  override val item: Option[Comestible] = Option(new Comestible(nombre = "comestible", hambreDisminuible = 5.0)) // overrideo el item para que sea del tipo Option[Comestible]
  
  override def aumentarHambre(porcentaje: Double): Vikingo = super.disminuirHambre(porcentaje*2)

  override def postParticipar(): Vikingo = item.map(_.comer(this)).getOrElse(this)
  
  override def porcentajeHambreMaximo: Double = 50.0
}