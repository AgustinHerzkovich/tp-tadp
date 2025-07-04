package entidades.torneo.reglas

import entidades.participantes.{Participante, Vikingo}

class ReglaEstandar extends Regla  {
  def quienGana(participantes: List[_ <: Participante]): Participante = participantes.collectFirst { case v: Vikingo => v }.get
  
  def quienesParticipan(participantes: List[Participante]): List[Vikingo] = participantes.collect { case v: Vikingo => v }
}