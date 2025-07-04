package entidades.dragones

import entidades.participantes.Vikingo
import entidades.requisitos.{Requisito, RequisitoPesoMaximo}

abstract class Dragon(val peso: Double, val requisitosExtra: List[Requisito], val danio: Double, val velocidadBase: Double = 60) {

  def requisitos: List[Requisito] = List(RequisitoPesoMaximo(cargaMaxima)) ++ requisitosExtra

  def velocidad: Double = velocidadBase - peso

  def cargaMaxima: Double = {
    peso * 0.2
  }

  def puedeSerMontado(vikingo : Vikingo): Boolean = requisitos.forall(_.apply(vikingo))
}