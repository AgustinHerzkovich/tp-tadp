package entidades.dragones

import entidades.requisitos.{Requisito, RequisitoPesoMaximo}


class Gronckle(override val peso: Double, pesoMaximoVikingo : Double, override val requisitosExtra: List[Requisito] = List()) extends Dragon(peso, List(RequisitoPesoMaximo(pesoMaximoVikingo)) ++ requisitosExtra, 5 * peso, velocidadBase = 30)