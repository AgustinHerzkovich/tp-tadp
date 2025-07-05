package entidades.torneo

import entidades.dragones.Dragon
import entidades.participantes.{Equipo, Vikingo}
import entidades.postas.Posta

class TorneoEquipos(val postas: List[Posta], val dragones: List[Dragon]) extends Torneo[Equipo] {
  
  protected def quienesParticipan(participantes: List[Equipo]): List[Vikingo] =
    participantes.flatMap(e => e.miembros)

  protected def quienGana(vikingos: List[Vikingo]): Option[Equipo] = {
    val equipos: List[Equipo] = reagruparEnEquipos(vikingos) // Solo reagrupo al final del torneo para contar cuántos miembros hay en cada equipo
    equipos.maxByOption(_.miembros.length) // Gana aquel con mayor cantidad de miembros; en caso de empate, retorna el primero que encontró maxBy
  }

  private def reagruparEnEquipos(vikingos: List[Vikingo]): List[Equipo] = {
    val agrupados: Map[String, List[Vikingo]] = vikingos.groupBy(_.equipo.get.nombre) // Agrupa los vikingos según el equipo al que pertenecen (por nombre)
    agrupados.map { case (nombre, miembros) => Equipo(miembros, nombre) }.toList
    // Para cada entrada del Map (nombre del equipo original y sus miembros actuales),
    // se crea un nuevo objeto Equipo con los miembros actualizados (los que sobrevivieron la posta)
    // y se mantiene el nombre del equipo original
  }
}