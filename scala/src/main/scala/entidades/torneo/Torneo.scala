package entidades.torneo

import entidades.participantes.Vikingo
import entidades.dragones.Dragon
import entidades.torneo.postas.Posta
import entidades.torneo.reglas.Regla

class Torneo(val postas: List[Posta], val dragones: List[Dragon], regla: Regla) {
  def apply(vikingos : List[Vikingo]): Option[Vikingo] = {
    val dragonesDisponibles: List[Dragon] = regla.dragonesDisponibles(dragones)
    val vikingosQueParticipan: List[Vikingo] = regla.quienesParticipan(vikingos)
    realizarRondas(vikingosQueParticipan, postas, dragonesDisponibles)
  }

  private def realizarRondas(vikingos: List[Vikingo], postas: List[Posta], dragonesDisponibles: List[Dragon]): Option[Vikingo] = {
    val vikingosFinales = postas.foldLeft(vikingos) { (participantes, posta) =>
      val despuesDePosta = posta(participantes, dragonesDisponibles)
      regla.aprobados(despuesDePosta)
    }

    Option.when(vikingosFinales.nonEmpty)(regla.quienGana(vikingosFinales))
  }

/* version vieja
  // TODO: Pasar esto a Fold
  private def realizarRondas(vikingos : List[Vikingo], postas : List[Posta], dragonesDiponibles : List[Dragon]): Option[Vikingo] = (vikingos, postas) match {
    case (Nil, _) => Option(null) // Esta mal pattern matchear asi igual
    case (vikingo :: Nil, _)  => Option(vikingo)
    case (_, Nil) => Option(regla.quienGana(vikingos))
    case (vikingos, posta :: siguientes)  =>  {
      val vikingosOrdenadosParaMontar: List[Vikingo] = regla.ordenDeMonturas(vikingos)
      val vikingosDespuesDe: List[Vikingo] = posta(vikingosOrdenadosParaMontar, dragonesDiponibles)
      val vikingosQuePasan: List[Vikingo] = regla.quienesPasanDeRonda(vikingosDespuesDe)
      realizarRondas(vikingosQuePasan, siguientes, dragonesDiponibles)
    }
  }
  */
/* otra alternativa
  private def realizarRondas(vikingos: List[Vikingo], postas: List[Posta], dragonesDisponibles: List[Dragon]): Option[Vikingo] = {
  if (vikingos.isEmpty) Option.empty
  else if (vikingos.size == 1 || postas.isEmpty)
    Option(regla.quienGana(vikingos))
  else {
    val vikingosMontura = regla.ordenDeMonturas(vikingos)
    val vikingosPosta = postas.head(vikingosMontura, dragonesDisponibles)
    val vikingosQuePasan = regla.quienesPasanDeRonda(vikingosPosta)
    realizarRondas(vikingosQuePasan, postas.tail, dragonesDisponibles)
  }
}

 */
}