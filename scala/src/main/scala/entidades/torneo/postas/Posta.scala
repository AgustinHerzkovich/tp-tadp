package entidades.torneo.postas

import entidades.participantes.{Individuo, Jinete, Vikingo}
import entidades.dragones.Dragon
import entidades.requisitos.Requisito

abstract class Posta {

  def apply(vikingos: List[Vikingo], dragones: List[Dragon]): List[Vikingo] = {
    val individuos = armarIndividuos(vikingos, dragones)
      .sortWith((c1, c2) => c1.esMejorQue(c2)(this))

    desmontarIndividuos(individuos)
      .map(vikingo => vikingo
        .aumentarHambre(hambreQueGenera())
        .accionLuegoDeParticiparEnPosta()) // Acciones que se realizan luego de participar
  }
  
  protected def hambreQueGenera(): Double

  protected def requisitoDeParticipacion(): Requisito

  protected def puedeParticipar(individuo: Individuo): Boolean = requisitoDeParticipacion()(individuo) && !individuo.aumentarHambre(hambreQueGenera()).estaHambriento()

  private def desmontarIndividuos(individuos: List[Individuo]): List[Vikingo] = individuos.map {
    case jinete: Jinete => jinete.vikingo // Desmontar al jinete devuelve su vikingo
    case vikingo: Vikingo => vikingo
  }
  
  private def armarIndividuos(vikingos: List[Vikingo], dragonesDisponibles: List[Dragon]): List[Individuo] = {
    val (individuos, _) = vikingos.foldLeft((List.empty[Individuo], dragonesDisponibles)) {
      case ((individuosAcumulados, dragonesRestantes), vikingo) => //parametros de lambda, descomponemos tupla
        // armarIndividuo devuelve Option[Individuo], fold solo ejecuta si hay un Individuo 
        mejorOpcion(vikingo, dragonesRestantes).fold((individuosAcumulados, dragonesRestantes)) {
          case jinete: Jinete =>
            (individuosAcumulados :+ jinete, dragonesRestantes.filterNot(_ eq jinete.dragon))
          case vik: Vikingo =>
            (individuosAcumulados :+ vik, dragonesRestantes)
        }
    }
    
    individuos
  }

  private def mejorOpcion(vikingo: Vikingo, dragones: List[Dragon]): Option[Individuo] = {
    val opciones = vikingo :: dragones.flatMap(d => vikingo
      .montar(d)
      .toOption)

    opciones
      .filter(puedeParticipar)
      .reduceOption((c1, c2) => if (c1.esMejorQue(c2)(this)) c1 else c2) // Te devuelve el mejor segun criterio o none
  }
}