import entidades.dragones.FuriaNocturna
import entidades.items.Arma
import entidades.participantes.Vikingo
import entidades.requisitos.RequisitoItem
import entidades.torneo.Torneo
import entidades.torneo.postas.Combate
import entidades.torneo.reglas.{ReglaEliminacion, ReglaEstandar, ReglaHandicap, ReglaTorneoInverso, ReglaVetoDragones}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.*

class Requerimiento5 extends AnyFlatSpec with Matchers {

  val espada: Arma = Arma(nombre = "Espada", danio = 30)
  val dragones: List[FuriaNocturna] = List.fill(3)(FuriaNocturna(peso = 100, danio = 50))
  val postaGenerica: Combate =  Combate(5, new RequisitoItem(_ => true)) // deja pasar a todos

  val v1: Vikingo = Vikingo(20, 70, 30, 10, Option(espada))
  val v2: Vikingo = Vikingo(22, 70, 30, 10, Option(espada))
  val v3: Vikingo = Vikingo(24, 70, 30, 10, Option(espada))
  val v4: Vikingo = Vikingo(26, 70, 30, 10, Option(espada))

  val todos: List[Vikingo] = List(v1, v2, v3, v4)

  "ReglaEstandar" should "eliminar a la mitad inferior" in {
    val regla = new ReglaEstandar
    val torneo = new Torneo(List(postaGenerica), dragones, regla)
    val resultado = torneo(todos)
    resultado shouldBe Option(regla.quienGana(List(v1, v2))) // los dos primeros sobreviven
  }

  "ReglaEliminacion" should "eliminar cantidad fija" in {
    val regla = new ReglaEliminacion(3)
    val torneo = new Torneo(List(postaGenerica), dragones, regla)
    val resultado = torneo(todos)
    resultado shouldBe Option(v1) // solo queda el primero
  }

  "ReglaHandicap" should "invertir el orden de montura" in {
    val regla = new ReglaHandicap
    val torneo = new Torneo(List(postaGenerica), dragones, regla)
    // Verificamos que el orden fue invertido al montar
    val resultado = torneo(todos)
    resultado shouldBe Option(regla.quienGana(List(v4, v3))) // debería quedar el grupo montado al revés
  }

  "ReglaTorneoInverso" should "mantener a la mitad inferior y elegir al último" in {
    val regla = new ReglaTorneoInverso
    val torneo = new Torneo(List(postaGenerica), dragones, regla)
    val resultado = torneo(todos)
    resultado shouldBe Option(v4) // se queda con la mitad más débil, el último gana
  }

  "ReglaVetoDragones" should "solo dejar dragones válidos" in {
    val dragonesPermitidos = List(
      FuriaNocturna(peso = 100, danio = 50),
      FuriaNocturna(peso = 200, danio = 10)
    )
    val veto = new ReglaVetoDragones(_.danio > 20)
    val torneo = new Torneo(List(postaGenerica), dragonesPermitidos, veto)
    val resultado = torneo(todos)
    // solo quedan los que lograron montar dragones con danio > 20
    resultado.isDefined shouldBe true
  }
}