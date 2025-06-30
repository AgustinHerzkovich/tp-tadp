package entidades.participantes.obj

import entidades.items.SistemaDeVuelo
import entidades.participantes.Vikingo

object Hipo extends Vikingo(100.0, 100.0, 100.0, 0.0){
    override val item: Option[SistemaDeVuelo] = Option(new SistemaDeVuelo()) // overrideo el item para que sea del tipo Option[SistemaDeVuelo]
}
