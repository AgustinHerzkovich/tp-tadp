import entidades.participantes.{Jinete, Individuo, Vikingo}
import entidades.dragones.{FuriaNocturna, Gronckle, NadderMortifero}
import entidades.items.Arma
import entidades.torneo.postas.Pesca
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

import scala.language.postfixOps

class Requerimiento3 extends AnyFlatSpec {
  val vikingo: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Option(new Arma(nombre = "espada suprema", danio = 100)))
  val dragon1: FuriaNocturna = new FuriaNocturna(peso = 5000, danio = 1000)
  val dragon2: Gronckle = new Gronckle(peso = 5000, pesoMaximoVikingo = 500)
  val dragon3: NadderMortifero = new NadderMortifero(peso = 50)
  val posta: Pesca = new Pesca(hambreQueGenera = 2, Option())

  "vikingo se jinetea" should "el vikingo puede montar a algún dragón, entonces se obtiene el jinete" in{
    posta.armarCompetidor(vikingo, List(dragon1, dragon2)) match {
      case j: Jinete =>
        j.dragon shouldBe dragon1
        j.dragon.puedeSerMontado(j.vikingo) shouldBe true
      case _ => fail("Se esperaba un jinete y no lo fue")
    }
  }

  "vikingo no se jinetea porque es mejor" should "vikingo puede montar a algún dragón, pero a pesar de eso le conviene estar solo" in {
    val vikingo1: Individuo = posta.armarCompetidor(vikingo, List(dragon3, dragon2))
    vikingo1 shouldBe a [Vikingo]
    vikingo1 shouldBe vikingo
    dragon2.puedeSerMontado(vikingo1.asInstanceOf[Vikingo]) shouldBe true
  }

  "vikingo no se jinetea porque no puede montar" should "vikingo no puede montar a ningún dragón, entonces su única opción es competir solo" in {
    val vikingo1: Individuo = posta.armarCompetidor(vikingo, List(dragon3))
    vikingo1 shouldBe a [Vikingo]
    dragon3.puedeSerMontado(vikingo1.asInstanceOf[Vikingo]) shouldBe false
  }
}
