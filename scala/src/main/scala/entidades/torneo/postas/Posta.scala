package entidades.torneo.postas

import entidades.participantes.{Individuo, Jinete, Vikingo}
import entidades.dragones.Dragon
import entidades.requisitos.Requisito

abstract class Posta {

  val hambreQueGenera: Double

  val requisitoDeParticipacion: Requisito

  // Aplica la posta: arma los participantes posibles (vikingos o jinetes), ordena por rendimiento,
  // los desmonta y les aplica los efectos post-participación (hambre + posibles acciones)
  def apply(vikingos: List[Vikingo], dragones: List[Dragon]): List[Vikingo] = {
    val individuos = armarIndividuos(vikingos, dragones)
      .sortWith((c1, c2) => c1.esMejorQue(c2)(this))

    desmontarIndividuos(individuos)
      .map(vikingo => vikingo
        .aumentarHambre(hambreQueGenera) // Aplica el costo energético de participar
        .accionLuegoDeParticiparEnPosta()) // Hook para efectos personalizados luego de participar (como Patapez)
  }

  protected def puedeParticipar(individuo: Individuo): Boolean = requisitoDeParticipacion(individuo) && !individuo.aumentarHambre(hambreQueGenera).estaHambriento()

  // Convierte una lista de individuos en una lista de vikingos desmontados (para continuar el torneo)
  private def desmontarIndividuos(individuos: List[Individuo]): List[Vikingo] = individuos.map {
    case jinete: Jinete => jinete.vikingo // Desmontar al jinete devuelve su vikingo
    case vikingo: Vikingo => vikingo
  }

  // Arma la lista de individuos que efectivamente participan en esta posta, seleccionando
  // para cada vikingo su mejor forma de competir (montado o no), si es que puede hacerlo
  private def armarIndividuos(vikingos: List[Vikingo], dragonesDisponibles: List[Dragon]): List[Individuo] = {
    val (individuos, _) : (List[Individuo], List[Dragon]) = vikingos.foldLeft((List.empty[Individuo], dragonesDisponibles)) {
      case ((individuosAcumulados, dragonesRestantes), vikingo) => // Parámetros de lambda, descomponemos tupla
        mejorOpcion(vikingo, dragonesRestantes)
          .map(individuo => agregarIndividuo(individuosAcumulados, dragonesRestantes, individuo)) // Si hay una opción válida, la agregamos a la lista de individuos y actualizamos la lista de dragones
          .getOrElse((individuosAcumulados, dragonesRestantes)) // Si no hay forma válida de participar, mantenemos las listas como estaban
    }
    individuos
  }

  // Dado un individuo (vikingo o jinete), lo agrega a la lista y actualiza la lista de dragones si corresponde
  private def agregarIndividuo(individuosAcumulados: List[Individuo], dragones: List[Dragon], individuo: Individuo): (List[Individuo], List[Dragon]) = individuo match {
    case jinete: Jinete =>
      (individuosAcumulados :+ jinete, dragones.filterNot(_ eq jinete.dragon)) // Si el vikingo monta un dragón, se remueve ese dragón de los disponibles
    case vik: Vikingo =>
      (individuosAcumulados :+ vik, dragones) // Si participa sin montar, la lista de dragones queda igual
  }

  // Devuelve la mejor forma en la que un vikingo puede participar: montado o no
  // Evalúa todas las opciones posibles y elige la mejor según la posta (siempre que cumpla los requisitos)
  private def mejorOpcion(vikingo: Vikingo, dragones: List[Dragon]): Option[Individuo] = {
    val opciones: List[Individuo] = vikingo :: dragones.flatMap(d => vikingo.montar(d).toOption)

    opciones
      .filter(puedeParticipar)
      .reduceOption((c1, c2) => if (c1.esMejorQue(c2)(this)) c1 else c2)
      // Te devuelve un Option, con el mejor según el criterio, o None si la lista está vacía
  }
}