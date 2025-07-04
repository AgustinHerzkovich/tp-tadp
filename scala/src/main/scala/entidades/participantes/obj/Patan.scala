package entidades.participantes.obj

import entidades.items.Arma
import entidades.participantes.Vikingo

object Patan extends Vikingo(velocidad = 100.0, peso = 80.0, barbarosidad = 100.0, porcentajeHambre = 0.0, item = Option(Arma(nombre = "maza", danio = 100.0)))