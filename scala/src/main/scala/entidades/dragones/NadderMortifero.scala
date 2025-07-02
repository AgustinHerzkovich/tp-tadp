package entidades.dragones

import entidades.requisitos.{Requisito, RequisitoDanio}

class NadderMortifero(override val peso: Double, override val requisitosExtra: List[Requisito] = List(new RequisitoDanio(150.0)), override val danio: Double = 150.0) extends Dragon(peso, requisitosExtra, danio)