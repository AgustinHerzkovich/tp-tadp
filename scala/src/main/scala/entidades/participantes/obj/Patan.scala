package entidades.participantes.obj

import entidades.items.Arma
import entidades.participantes.Vikingo

object Patan extends Vikingo(100.0, 100.0, 100.0, 0.0){
    override val item: Option[Arma] = Option(new Arma(nombre = "maza", danio = 100.0))
}
