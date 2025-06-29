package entidades.dragones

import entidades.requisitos.{Requisito, RequisitoDanio, RequisitoPesoMaximo}



case class Gronckle(     override val peso: Double,
                    override val requisitosExtra: List[Requisito] = List(),
                    val pesoMaximoVikingo : Double
                   ) extends Dragon(peso, List(new RequisitoPesoMaximo(pesoMaximoVikingo)) ++ requisitosExtra, 5 * peso, 30){

}



