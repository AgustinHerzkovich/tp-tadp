package entidades.torneo.postas

import entidades.competidores.{Competidor, Jinete, Vikingo}
import entidades.dragones.Dragon
import entidades.requisitos.{Requisito, RequisitoHambreMaxima}

//TODO : VER SI PODRIA SER CON MIXIN
abstract class Posta(val hambreQueGenera: Double/*, val requisitoPost: RequisitoHambreMaxima = new RequisitoHambreMaxima(hambreMaxima = 100)*/) {

  def hacerParticipar(competidor: Competidor): Competidor = competidor.aumentarHambre(hambreQueGenera) // EL COPY SE HACE ADENTRO DE AUMENTARAMBREEEE

  def puedeParticipar(competidor: Competidor): Boolean =
    cumplePre(competidor) && cumplePost(hacerParticipar(competidor))

  def cumplePre(competidor: Competidor): Boolean // Abstracto

  //def cumplePost(competidor: Competidor): Boolean = requisitoPost.cumple(competidor)
  def cumplePost(competidor: Competidor): Boolean = competidor.puedeSeguir()

  def obtenerVikingos(competidores: List[Competidor]): List[Vikingo] = competidores.map(competidor => obtenerVikingo(competidor))

  def obtenerVikingo(competidor: Competidor): Vikingo = competidor match {
    case competidor: Jinete => competidor.vikingo // Desmontar
    case competidor: Vikingo => competidor
  }

  def realizarse(vikingos: List[Vikingo], dragones: List[Dragon]): List[Vikingo] = {
    var competidores: List[Competidor] = armarCompetidores(vikingos, dragones)
    competidores = participantesOrdenados(competidores)
    obtenerVikingos(competidores).map(vikingo => vikingo.postParticipar()) // Acciones que se realizan luego de participar
  }


  def armarCompetidores(vikingos: List[Vikingo], dragonesDisponibles: List[Dragon]): List[Competidor] = (vikingos, dragonesDisponibles) match{
    case (Nil, _) => Nil
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

  def armarCompetidor(vikingo: Vikingo, dragones: List[Dragon]): Competidor = {
    val opciones: List[Competidor] = vikingo :: dragones.map(d => vikingo.montar(d).getOrElse({vikingo})) // si no puede montar el dragon, vuelve a figurar como él solo
    participantesOrdenados(opciones).filter(puedeParticipar).head
  }

  def participantesOrdenados(competidores: List[Competidor]) : List[Competidor] = {
    competidores.sortWith((c1, c2) => c1.esMejorQue(c2)(this)) // ACA PASAMOS LISTA DE COMPETIDORES AL TORNEO PARA QUE LUEGO LA REGLA FILTRE EVERYTHING
  }

}










