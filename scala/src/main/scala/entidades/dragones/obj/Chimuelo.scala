package entidades.dragones.obj

import entidades.dragones.FuriaNocturna
import entidades.items.SistemaDeVuelo
import entidades.requisitos.RequisitoItem

object Chimuelo extends FuriaNocturna(100.0, List(new RequisitoItem(_.isInstanceOf[SistemaDeVuelo])), 100.0)