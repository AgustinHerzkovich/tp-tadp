package entidades.participantes.obj

import entidades.items.Arma
import entidades.participantes.Vikingo

object Astrid extends Vikingo(velocidad = 100.0, peso = 100.0, barbarosidad = 100.0, porcentajeHambre = 0.0, item = Option(new Arma(nombre = "hacha", danio = 30.0)))