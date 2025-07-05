import entidades.participantes.{Individuo, Vikingo}
import entidades.dragones.{Dragon, Gronckle}
import entidades.requisitos.RequisitoItem
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import entidades.items.Arma
import entidades.postas.Combate

import scala.language.postfixOps

class Requerimiento2 extends AnyFlatSpec {
  val vikingo1: Vikingo = Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 60, item = Option(Arma(nombre = "espada suprema", danio = 1000)))
  val vikingoQueProduceMuchoDanio: Vikingo = Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Option(Arma(nombre = "espada suprema", danio = 1000000)))
  val vikingoQueProducePocoDanio: Vikingo = Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Option(Arma(nombre = "espada suprema", danio = 1)))
  val combate: Combate = Combate(hambreQueGenera = 50, requisitoDeParticipacion = RequisitoItem[Individuo](i => i.isInstanceOf[Arma] && i.nombre == "espada suprema"))
  val dragon1: Gronckle = Gronckle(peso = 10, pesoMaximoVikingo = 100)
  val dragon2: Gronckle = Gronckle(peso = 20, pesoMaximoVikingo = 100)
  val listaVikingos: List[Vikingo] = List(vikingo1, vikingoQueProduceMuchoDanio, vikingoQueProducePocoDanio)
  val listaDragones: List[Dragon] = List(dragon1, dragon2)

  "vikingoQueProduceMuchoDanio es mejor que vikingoQueProducePocoDanio" should "retorna true porque en un combate, es mejor aquel participante que haga más daño" in {
    vikingoQueProduceMuchoDanio.esMejorQue(vikingoQueProducePocoDanio)(combate) shouldBe true
  }

  "varios participantes participan de una posta" should "como resultado quedan solo dos participantes de los 3 que participaron inicialmente" in {
    val ganadoresPosta: List[Individuo] = combate(listaVikingos, listaDragones)
    ganadoresPosta should have size 2
    ganadoresPosta.head.porcentajeHambre shouldBe (70.0 +- 0.0001)
  }
}