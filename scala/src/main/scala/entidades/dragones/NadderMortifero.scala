package entidades.dragones

import entidades.requisitos.{Requisito, RequisitoDanio}

case class NadderMortifero(override val peso: Double,
                           override val requisitosExtra: List[Requisito] = List()
                          ) extends Dragon(peso, List(new RequisitoDanio(150.0)) ++ requisitosExtra, 150.0){
}




