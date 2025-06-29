package entidades.requisitos



//TODO: consultar esta version
/*
import individuoes.individuo
import items.Item

type Requisito = individuo => Boolean

object RequisitoImpl{

  def porBarbarosidad(minimo : Double): Requisito = {
    (individuo: individuo) => individuo.barbarosidad >= minimo
  }

  def porItem (item : Option[Item]) : Requisito = {
    (individuo: individuo) => individuo.arma == item
  }

  def porDanioMaximo(danio : Double) : Requisito ={
    (individuo : individuo) => individuo.danio <= danio
  }

  def porPesoMaximo(peso: Double): Requisito = {
    (individuo: individuo) => individuo.peso <= peso
  }

  def porPesoMinimoDePesca(pesoMinimo: Double): Requisito = {
    (individuo: individuo) => individuo.cargaMaxima >= pesoMinimo
  }

}

trait MonturaConRequisitos {

  //Podria no instanciarle nada a esto y que sea un trait que se use en las clases que lo necesiten
  def requisitosExtras : List[Requisito] = List()

  def puedeSerMontadoPor(individuo: individuo): Boolean = {
    requisitosExtras.forall(requisito => requisito(individuo))
  }

}

trait PostaConRequisitos {

  def requisitosExtras: List[Requisito] = List()

  def puedeParticipar(individuo: individuo): Boolean = {
    requisitosExtras.forall(requisito => requisito(individuo))
  }

  // def atravesar(individuoes: List[individuo]): List[individuo] = {
  // individuoes.filter(puedeParticipar)
  //}

}

*/