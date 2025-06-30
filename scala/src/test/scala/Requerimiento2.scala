import entidades.participantes.{Individuo, Vikingo}
import entidades.dragones.{Dragon, FuriaNocturna, Gronckle}
import entidades.requisitos.{RequisitoCargaMinima, RequisitoItem}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import entidades.items.Arma
import entidades.torneo.postas.*

import scala.language.postfixOps

class Requerimiento2 extends AnyFlatSpec {
  val vikingo1: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 60, item = Option(new Arma(nombre = "espada suprema", danio = 1000)))
  val vikingoQueProduceMuchoDanio: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Option(new Arma(nombre = "espada suprema", danio = 1000000)))
  val vikingoQueProducePocoDanio: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Option(new Arma(nombre = "espada suprema", danio = 1)))
  val combate: Combate = new Combate(hambre = 5, requisito = new RequisitoItem(i => i.isInstanceOf[Arma] && i.nombre == "espada suprema"))
  val dragon1: Gronckle = new Gronckle(peso = 10, pesoMaximoVikingo = 100)
  val dragon2: Gronckle = new Gronckle(peso = 20, pesoMaximoVikingo = 100)
  val listaVikingos: List[Vikingo] = List(vikingo1, vikingoQueProduceMuchoDanio, vikingoQueProducePocoDanio)
  val listaDragones: List[Dragon] = List(dragon1, dragon2)

  "vikingoQueProduceMuchoDanio es mejor que vikingoQueProducePocoDanio" should "retorna true porque en un combate, es mejor aquel participante que haga más daño" in {
    vikingoQueProduceMuchoDanio.esMejorQue(vikingoQueProducePocoDanio)(combate) shouldBe true
  }

  "varios participantes participan de una posta" should "como resultado quedan solo dos participantes de los 3 que participaron inicialmente" in {
    val ganadoresPosta: List[Individuo] = combate(listaVikingos, listaDragones)
    ganadoresPosta.length shouldBe 2
    ganadoresPosta shouldBe ganadoresPosta.sortWith((g1, g2) => g1.esMejorQue(g2)(combate))
    ganadoresPosta.head.porcentajeHambre shouldBe (25.0 +- 0.0001)
  }
}