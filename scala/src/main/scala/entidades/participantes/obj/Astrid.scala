package entidades.participantes.obj

import entidades.items.Arma
import entidades.participantes.Vikingo

object Astrid extends Vikingo(100.0, 100.0, 100.0, 0.0){
    override val item: Option[Arma] = Option(new Arma(nombre = "hacha", danio = 30.0)) // overrideo el item para que sea del tipo Option[Arma]
}
