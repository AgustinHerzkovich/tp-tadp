package entidades.torneo

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon
import entidades.torneo.postas.Posta
import entidades.torneo.reglas.Regla

class Torneo(val postas: List[Posta], val dragones: List[Dragon], regla: Regla) { // Si la regla es de equipos, entonces el torneo se juega por equipos
  def apply(participantes : List[Vikingo]): Option[Vikingo] = {
    val dragonesDisponibles: List[Dragon] = regla.dragonesDisponibles(dragones)
    //val participantesValidos = regla.participantesValidos(participantes) // Filtrar participantes que no cumplen con la regla
    //val vikingosQueParticipan: List[Vikingo] = regla.prepararVikingos(participantesValidos) // Desarmar equipos o nada

    val vikingosFinales = postas.foldLeft(participantes) { (participantes, posta) =>
      if (participantes.size == 1) {
        participantes
      } else {
        val despuesDePosta = posta(participantes, dragonesDisponibles)
        regla.aprobados(despuesDePosta)
      }
    }

    Option.when(vikingosFinales.nonEmpty)(regla.quienGana(vikingosFinales))
  }
}
/*
Por Equipo:
  Los vikingos pueden tener equipo
  Al torneo se inscriben equipos
  Participar implica que cada jugador eliga un dragon
  Se elimina la peor mitad de jugadores
  Se reagrupan los restantes para la siguiente ronda
  Si quedan varios equipos gana el de más jugadores
  Si hay varios se elige cualquiera arbitrariamente
*/

/*
Opcion 1:
  Tenemos equipo: Option[String] en Vikingo
  participantesValidos: List[Vikingo] = participantes.filter(_.equipo.isDefined)
  No hace falta usar prepararVikingos

Opcion 2:
  Tenemos objeto equipo que es una lista de vikingos,

*/