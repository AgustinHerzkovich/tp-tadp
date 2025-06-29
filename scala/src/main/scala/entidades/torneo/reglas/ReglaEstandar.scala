package entidades.torneo.reglas

import entidades.participantes.Vikingo
import entidades.dragones.Dragon

class ReglaEstandar extends Regla  {
  def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)

  protected def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.head // Cambiar por TRY o algo así por si es empty

  def quienesParticipan(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones
}
