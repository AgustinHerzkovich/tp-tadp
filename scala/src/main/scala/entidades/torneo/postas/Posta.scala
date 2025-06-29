package entidades.torneo.postas

import entidades.competidores.{Competidor, Jinete, Vikingo}
import entidades.dragones.Dragon
import entidades.requisitos.{Requisito, RequisitoHambreMaxima}

abstract class Posta {

  protected def hambreQueGenera(): Double

  protected def requisitoDeParticipacion(): Requisito
  
  protected def hacerParticipar(competidor: Competidor): Competidor = competidor.aumentarHambre(hambreQueGenera())

  protected def puedeParticipar(competidor: Competidor): Boolean = requisitoDeParticipacion()(competidor) && hacerParticipar(competidor).puedeSeguir()

  protected def obtenerVikingos(competidores: List[Competidor]): List[Vikingo] = competidores.map(competidor => obtenerVikingo(competidor))

  protected def obtenerVikingo(competidor: Competidor): Vikingo = competidor match {
    case competidor: Jinete => competidor.vikingo // Desmontar
    case competidor: Vikingo => competidor
  }

  def apply(vikingos: List[Vikingo], dragones: List[Dragon]): List[Vikingo] = {
    var competidores: List[Competidor] = armarCompetidores(vikingos, dragones)
    competidores = participantesOrdenados(competidores)
    obtenerVikingos(competidores).map(vikingo => vikingo.postParticipar()) // Acciones que se realizan luego de participar
  }

  protected def armarCompetidores(vikingos: List[Vikingo], dragonesDisponibles: List[Dragon]): List[Competidor] = (vikingos, dragonesDisponibles) match{
    case (Nil, _) => Nil // Esa mal
    case (vikingos, Nil) => vikingos
    case (vikingo :: demasVikingos, dragonesDisponibles) => {
      val competidor = armarCompetidor(vikingo, dragonesDisponibles)
      competidor match {
        case competidor: Vikingo => competidor :: armarCompetidores(demasVikingos, dragonesDisponibles)
        case competidor: Jinete => competidor :: armarCompetidores(demasVikingos, dragonesDisponibles.filterNot(_ == competidor.dragon))
        // puede pasar que el vikingo compita mejor sin dragon y vaya solo
      }
    }
  }

  protected def armarCompetidor(vikingo: Vikingo, dragones: List[Dragon]): Competidor = {
    val opciones: List[Competidor] = vikingo :: dragones.map(d => vikingo.montar(d).getOrElse({vikingo})) // si no puede montar el dragon, vuelve a figurar como él solo
    participantesOrdenados(opciones).filter(puedeParticipar).head
  }

  protected def participantesOrdenados(competidores: List[Competidor]) : List[Competidor] = {
    competidores.sortWith((c1, c2) => c1.esMejorQue(c2)(this)) // ACA PASAMOS LISTA DE COMPETIDORES AL TORNEO PARA QUE LUEGO LA REGLA FILTRE EVERYTHING
  }
}










