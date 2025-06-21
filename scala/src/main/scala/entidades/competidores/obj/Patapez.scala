package entidades.competidores.obj

import entidades.competidores.Vikingo
import entidades.items.{Comestible, SistemaDeVuelo}

object Patapez extends Vikingo(100.0, 100.0, 100.0, 0.0){
  override val item: Option[Comestible] = Some(new Comestible(5)) // overrideo el item para que sea del tipo Option[Comestible]
  override val porcentajeHambreMaximo = 50

  override def aumentarHambre(porcentaje: Double): Vikingo = super.disminuirHambre(porcentaje*2)

  override def postParticipar(): Vikingo = item.map(_.comer(this)).getOrElse(this)
}
