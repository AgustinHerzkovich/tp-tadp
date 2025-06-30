package entidades.dragones

import entidades.requisitos.{Requisito, RequisitoDanio, RequisitoPesoMaximo}



case class Gronckle(override val peso: Double,
                    pesoMaximoVikingo : Double,
                    override val requisitosExtra: List[Requisito]
                   ) extends Dragon(peso, List(new RequisitoPesoMaximo(pesoMaximoVikingo)), 5 * peso, 30){

}