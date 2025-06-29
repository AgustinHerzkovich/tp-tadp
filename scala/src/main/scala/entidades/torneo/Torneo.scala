package entidades.torneo

import entidades.participantes.Vikingo
import entidades.dragones.Dragon
import entidades.torneo.postas.Posta
import entidades.torneo.reglas.Regla

class Torneo(val postas: List[Posta], val dragones: List[Dragon], regla: Regla) {
  def apply(vikingos : List[Vikingo]): Option[Vikingo] = {
    val dragonesDisponibles: List[Dragon] = regla.dragonesDisponibles(dragones)
    val vikingosQueParticipan: List[Vikingo] = regla.quienesParticipan(vikingos)
    realizarRonda(vikingosQueParticipan, postas, dragonesDisponibles)
  }

  // TODO: Pasar esto a Fold
  private def realizarRonda(vikingos : List[Vikingo], postas : List[Posta], dragonesDiponibles : List[Dragon]): Option[Vikingo] = (vikingos, postas) match {
    case (Nil, _) => Option() // Esta mal pattern matchear asi igual
    case (vikingo :: Nil, _)  => Option(vikingo)
    case (_, Nil) => Option(regla.quienGana(vikingos))
    case (vikingos, posta :: siguientes)  =>  {
      val vikingosOrdenadosParaMontar: List[Vikingo] = regla.ordenDeMonturas(vikingos)
      val vikingosDespuesDe: List[Vikingo] = posta(vikingosOrdenadosParaMontar, dragonesDiponibles)
      val vikingosQuePasan: List[Vikingo] = regla.quienesPasanDeRonda(vikingosDespuesDe)
      realizarRonda(vikingosQuePasan, siguientes, dragonesDiponibles)
    }
  }
/* otra opcion
  private def realizarRonda(vikingos: List[Vikingo], postas: List[Posta], dragonesDiponibles: List[Dragon]): Option[Vikingo] = {
    if (vikingos.isEmpty) Option()
    else if (vikingos.size == 1 || postas.isEmpty) Option(regla.quienGana(vikingos))
    else  {
      val vikingosOrdenadosParaMontar = regla.ordenDeMonturas(vikingos)
      val vikingosDespuesDe = postas.head(vikingosOrdenadosParaMontar, dragonesDisponibles)
      val vikingosQuePasan = regla.quienesPasanDeRonda(vikingosDespuesDe)
      realizarRonda(vikingosQuePasan, postas.tail, dragonesDisponibles)
      }
    }
 */
}