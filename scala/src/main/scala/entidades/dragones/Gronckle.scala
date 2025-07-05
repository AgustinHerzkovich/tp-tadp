package entidades.dragones

import entidades.participantes.Vikingo
import entidades.requisitos.{Requisito, RequisitoPesoMaximo}


class Gronckle(override val peso: Double, pesoMaximoVikingo : Double, override val requisitosExtra: List[Requisito[Vikingo]] = List()) extends Dragon(peso, List(RequisitoPesoMaximo(pesoMaximoVikingo)) ++ requisitosExtra, 5 * peso, velocidadBase = 30)