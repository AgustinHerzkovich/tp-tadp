package entidades.dragones.obj

import entidades.dragones.FuriaNocturna
import entidades.items.SistemaDeVuelo
import entidades.participantes.Vikingo
import entidades.requisitos.RequisitoItem

object Chimuelo extends FuriaNocturna(peso = 5000.0, requisitosExtra = List(RequisitoItem[Vikingo](_.isInstanceOf[SistemaDeVuelo])), danio = 50.0)