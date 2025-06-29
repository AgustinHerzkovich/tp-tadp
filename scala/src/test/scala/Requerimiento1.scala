import entidades.participantes.{Jinete, Vikingo}
import entidades.dragones.{Dragon, FuriaNocturna, Gronckle}
import entidades.items.{Arma, Item}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.*

import scala.util.{Failure, Success, Try}

class Requerimiento1 extends AnyFlatSpec with Matchers {
  val vikingo: Vikingo = new Vikingo(velocidad = 100, peso = 100, barbarosidad = 50, porcentajeHambre = 20, item = Option(new Arma(nombre = "espada suprema", danio = 1000)))
  val unDragon: Dragon = new FuriaNocturna(peso = 5000, danio = 100)
  val otroDragon: Dragon = new Gronckle(peso = 100, pesoMaximoVikingo = 50)

  "jineteExitoso" should "retorna un jinete si el montado fue exitoso" in {
    val jineteExitoso: Try[Jinete] = vikingo.montar(unDragon)
    jineteExitoso.dragon shouldBe a [FuriaNocturna]
  }

  "jineteNoExitoso" should "retorna un failure si el montado no fue exitoso" in {
    val jineteNoExitoso: Try[Jinete] = vikingo.montar(otroDragon)
    jineteNoExitoso shouldBe a [Vikingo]
}
