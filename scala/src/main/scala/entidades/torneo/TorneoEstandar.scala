package entidades.torneo

import entidades.dragones.Dragon
import entidades.participantes.Vikingo
import entidades.postas.Posta

class TorneoEstandar(val postas: List[Posta], val dragones: List[Dragon]) extends Torneo[Vikingo] {

  protected def quienGana(vikingos: List[Vikingo]): Option[Vikingo] = vikingos.headOption

  protected def quienesParticipan(vikingos: List[Vikingo]): List[Vikingo] = vikingos
}