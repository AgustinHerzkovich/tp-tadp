package entidades.torneo

import entidades.participantes.{Participante, Vikingo}
import entidades.dragones.Dragon
import entidades.postas.Posta

abstract class Torneo[P <: Participante] {
  // El tipo depende de la subclase de Torneo

  val postas: List[Posta]
  val dragones: List[Dragon]

  // Ejecuta el torneo con una lista inicial de participantes
  // Devuelve el ganador si hay uno, o None si nadie llegó al final
  def apply(participantes: List[P]): Option[P] = {
    val dragonesQueParticipan: List[Dragon] = dragonesDisponibles(dragones)
    val vikingosQueParticipan: List[Vikingo] = quienesParticipan(participantes)

    // Aplica cada posta secuencialmente, actualizando los participantes según el desempeño
    val vikingosFinales = postas.foldLeft(vikingosQueParticipan) { (vikingos, posta) =>
      if (vikingos.size <= 1) {
        // Si queda uno solo en pie, no hace falta seguir jugando
        vikingos
      } else {
        val despuesDePosta = posta(vikingos, dragonesQueParticipan)
        aprobados(despuesDePosta) // Filtra según los criterios de la regla (quiénes siguen)
      }
    }

    // Si quedan participantes, se define un ganador con la regla; si no, no hay ganador
    quienGana(vikingosFinales)
  }

  private def aprobados(vikingos: List[Vikingo]): List[Vikingo] = ordenDeMonturas(quienesPasanDeRonda(vikingos))

  protected def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos

  protected def quienesPasanDeRonda(vikingos: List[Vikingo]): List[Vikingo] = vikingos.dropRight(vikingos.length / 2)

  protected def quienGana(vikingos: List[Vikingo]): Option[P] // Puede no haber ganado nadie

  protected def dragonesDisponibles(dragones: List[Dragon]): List[Dragon] = dragones

  protected def quienesParticipan(participantes: List[P]): List[Vikingo] // Deja los participantes como List[Vikingo]
}