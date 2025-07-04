package entidades.torneo.reglas.obj

import entidades.participantes.{Equipo, Participante, Vikingo}
import entidades.torneo.reglas.Regla

object ReglaEquipos extends Regla {
  def quienesParticipan(participantes: List[Participante]): List[Vikingo] =
    participantes.collect { case e: Equipo => e.miembros }.flatten

  private def reagruparEnEquipos(vikingos: List[Vikingo]): List[Equipo] = {
    val agrupados = vikingos.groupBy(_.equipo.getOrElse("Sin equipo"))
    agrupados.map { case (nombre, miembros) => Equipo(nombre, miembros) }.toList
  }

  def quienGana(participantes: List[_ <: Participante]): Participante = {
    val vikingos = participantes.collect { case v: Vikingo => v }
    val equipos = reagruparEnEquipos(vikingos)
    equipos.maxByOption(_.miembros.length).getOrElse(equipos.head)
  }
}