package entidades.torneo

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon
import entidades.torneo.postas.Posta
import entidades.torneo.reglas.Regla

class Torneo(val postas: List[Posta], val dragones: List[Dragon], regla: Regla) { // Si la regla es de equipos, entonces el torneo se juega por equipos
  // Ejecuta el torneo con una lista inicial de participantes
  // Devuelve el ganador si hay uno, o None si nadie llegó al final
  def apply(participantes : List[Participante]): Option[Participante] = {
    val dragonesDisponibles: List[Dragon] = regla.dragonesDisponibles(dragones)
    val vikingosQueParticipan: List[Vikingo] = regla.quienesParticipan(participantes)

    // Aplica cada posta secuencialmente, actualizando los participantes según el desempeño
    val vikingosFinales = postas.foldLeft(vikingosQueParticipan) { (vikingos, posta) =>
      if (vikingos.size <= 1) {
        // Si queda uno solo en pie, no hace falta seguir jugando
        vikingos
      } else {
        val despuesDePosta = posta(vikingos, dragonesDisponibles)
        regla.aprobados(despuesDePosta) // Filtra según los criterios de la regla (quiénes siguen)
      }
    }

    // Si quedan participantes, se define un ganador con la regla; si no, no hay ganador
    Option.when(vikingosFinales.nonEmpty)(regla.quienGana(vikingosFinales))
  }
}