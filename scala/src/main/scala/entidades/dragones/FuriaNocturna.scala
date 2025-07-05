package entidades.dragones

import entidades.participantes.Vikingo
import entidades.requisitos.Requisito

class FuriaNocturna(override val peso: Double, override val requisitosExtra: List[Requisito[Vikingo]] = List(), override val danio: Double) extends Dragon(peso, requisitosExtra, danio) {
  override def velocidad: Double = super.velocidad * 3
}