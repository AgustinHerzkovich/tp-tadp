package entidades.requisitos



//TODO: consultar esta version
/*
import competidores.Competidor
import items.Item

type Requisito = Competidor => Boolean

object RequisitoImpl{

  def porBarbarosidad(minimo : Double): Requisito = {
    (competidor: Competidor) => competidor.barbarosidad >= minimo
  }

  def porItem (item : Option[Item]) : Requisito = {
    (competidor: Competidor) => competidor.arma == item
  }

  def porDanioMaximo(danio : Double) : Requisito ={
    (competidor : Competidor) => competidor.danio <= danio
  }

  def porPesoMaximo(peso: Double): Requisito = {
    (competidor: Competidor) => competidor.peso <= peso
  }

  def porPesoMinimoDePesca(pesoMinimo: Double): Requisito = {
    (competidor: Competidor) => competidor.cargaMaxima >= pesoMinimo
  }

}

trait MonturaConRequisitos {

  //Podria no instanciarle nada a esto y que sea un trait que se use en las clases que lo necesiten
  def requisitosExtras : List[Requisito] = List()

  def puedeSerMontadoPor(competidor: Competidor): Boolean = {
    requisitosExtras.forall(requisito => requisito(competidor))
  }

}

trait PostaConRequisitos {

  def requisitosExtras: List[Requisito] = List()

  def puedeParticipar(competidor: Competidor): Boolean = {
    requisitosExtras.forall(requisito => requisito(competidor))
  }

  // def atravesar(competidores: List[Competidor]): List[Competidor] = {
  // competidores.filter(puedeParticipar)
  //}

}

*/