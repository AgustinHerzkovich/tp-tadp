package entidades.participantes.obj

import entidades.participantes.Vikingo
import entidades.items.{Comestible, SistemaDeVuelo}

object Patapez extends Vikingo(100.0, 100.0, 100.0, 0.0, porcentajeHambreMaximo = 50.0){
  override val item: Option[Comestible] = Option(new Comestible(5)) // overrideo el item para que sea del tipo Option[Comestible]
  
  override def aumentarHambre(porcentaje: Double): Vikingo = super.disminuirHambre(porcentaje*2)

  override def postParticipar(): Vikingo = item.map(_.comer(this)).getOrElse(this)
}
