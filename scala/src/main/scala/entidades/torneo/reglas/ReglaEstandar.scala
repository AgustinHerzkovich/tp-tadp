package entidades.torneo.reglas

import entidades.participantes.{Participante, Vikingo}

class ReglaEstandar extends Regla  {
  def quienGana(vikingos: List[Vikingo]): Vikingo = vikingos.head
  
  def quienesParticipan(participantes: List[Participante]): List[Vikingo] = participantes.collect { case v: Vikingo => v }
}