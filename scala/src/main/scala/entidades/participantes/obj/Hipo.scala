package entidades.participantes.obj

import entidades.items.SistemaDeVuelo
import entidades.participantes.Vikingo

object Hipo extends Vikingo(velocidad = 110.0, peso = 100.0, barbarosidad = 100.0, porcentajeHambre = 0.0, item = Option(new SistemaDeVuelo(nombre = "aerolineas argentinas")))