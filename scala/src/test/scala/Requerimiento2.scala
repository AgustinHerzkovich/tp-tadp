import entidades.competidores.{Competidor, Vikingo}
import entidades.dragones.{Dragon, FuriaNocturna, Gronckle}
import entidades.requisitos.{RequisitoCargaMinima, RequisitoItem}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import entidades.items.Arma
import entidades.torneo.postas.*

import scala.language.postfixOps

class Requerimiento2 extends AnyFlatSpec {
  val espada: Arma = new Arma(nombre = "espada suprema", danio = 1000)
  val vikingo1: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 60, item = Some(espada))
  val vikingoQueProduceMuchoDanio: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Some(new Arma(nombre = "espada suprema", danio = 1000000)))
  val vikingoQueProducePocoDanio: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Some(new Arma(nombre = "espada suprema", danio = 1)))
  val pesca: Pesca = new Pesca(hambreQueGenera = 5, preRequisito = Some(new RequisitoCargaMinima(cargaMinima = 500)))
  val combate: Combate = new Combate(hambreQueGenera = 5, preRequisito = Right(new RequisitoItem(espada)))
  val carrera: Carrera = new Carrera(hambreQueGenera = 50, None)
  val dragon1: Gronckle = new Gronckle(peso = 10, pesoMaximoVikingo = 100)
  val dragon2: Gronckle = new Gronckle(peso = 20, pesoMaximoVikingo = 100)
  val listaVikingos: List[Vikingo] = List(vikingo1, vikingoQueProduceMuchoDanio, vikingoQueProducePocoDanio)
  val listaDragones: List[Dragon] = List(dragon1, dragon2)

  "vikingo1 no participa en pesca" should "retorna false porque no puede participar en la pesca porque no cumple con la carga mínima" in {
    pesca.puedeParticipar(vikingo1) shouldBe false
  }

  "vikingo1 participa en combate" should "retorna true porque puede participar en el combate porque tiene arma" in {
    combate.puedeParticipar(vikingo1) shouldBe true
  }

  "vikingo1 no participa en carrera" should "retorna false porque no puede participar en la carrera porque quedaria con mas de 100% de hambre" in {
    carrera.puedeParticipar(vikingo1) shouldBe false
  }

  "vikingoQueProduceMuchoDanio es mejor que vikingoQueProducePocoDanio" should "retorna true porque en un combate, es mejor aquel participante que haga más daño" in {
    vikingoQueProduceMuchoDanio.esMejorQue(vikingoQueProducePocoDanio)(combate) shouldBe true
  }

  "varios participantes participan de una posta" should "como resultado quedan solo dos participantes de los 3 que participaron inicialmente" in {
    val ganadoresPosta: List[Competidor] = combate.realizarse(listaVikingos, listaDragones)
    ganadoresPosta.length shouldBe 2
    ganadoresPosta shouldBe ganadoresPosta.sortWith((g1, g2) => g1.esMejorQue(g2)(combate))
    ganadoresPosta.head.porcentajeHambre shouldBe (25.0 +- 0.0001)
  }
}