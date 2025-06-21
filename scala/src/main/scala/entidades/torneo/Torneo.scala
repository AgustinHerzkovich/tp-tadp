package entidades.torneo

import entidades.competidores.Vikingo
import entidades.dragones.Dragon
import entidades.torneo.postas.Posta
import entidades.torneo.reglas.Regla

class Torneo(val postas: List[Posta], val dragones: List[Dragon], regla: Regla) {
  def realizarTorneo(vikingos : List[Vikingo]): Option[Vikingo] = {
    val dragonesDisponibles: List[Dragon] = regla.dragonesDisponibles(dragones)
    val vikingosQueParticipan: List[Vikingo] = regla.quienesParticipan(vikingos)
    realizarRonda(vikingosQueParticipan, postas, dragonesDisponibles)
  }

  def realizarRonda(vikingos : List[Vikingo], postas : List[Posta], dragonesDiponibles : List[Dragon]): Option[Vikingo] = (vikingos, postas) match {
    case (Nil, _) => None
    case (vikingo :: Nil, _)  => Some(vikingo)
    case (_, Nil) => Some(regla.quienGana(vikingos))
    case (vikingos, posta :: siguientes)  =>  {
      val vikingosOrdenadosParaMontar: List[Vikingo] = regla.ordenDeMonturas(vikingos)
      val vikingosDespuesDe: List[Vikingo] = posta.realizarse(vikingosOrdenadosParaMontar, dragonesDiponibles)
      val vikingosQuePasan: List[Vikingo] = regla.quienesPasanDeRonda(vikingosDespuesDe)
      realizarRonda(vikingosQuePasan, siguientes, dragonesDiponibles)
    }
  }
}