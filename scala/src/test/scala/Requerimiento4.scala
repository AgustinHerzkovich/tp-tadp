import entidades.dragones.{Dragon, FuriaNocturna}
import entidades.items.Arma
import entidades.participantes.{Individuo, Vikingo}
import entidades.participantes.obj.{Astrid, Hipo, Patan, Patapez}
import entidades.postas.{Carrera, Combate, Pesca, Posta}
import entidades.requisitos.obj.NoRequisito
import entidades.requisitos.{RequisitoCargaMinima, RequisitoItem}
import entidades.torneo.TorneoEstandar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Requerimiento4 extends AnyFlatSpec with Matchers {

  val espada: Arma = Arma("Espada Valyria", 40.0)

  val pesca: Pesca = Pesca(hambreQueGenera = 5, requisitoDeParticipacion = RequisitoCargaMinima(200))
  val combate: Combate = Combate(hambreQueGenera = 5, requisitoDeParticipacion = RequisitoItem[Individuo](i => i.isInstanceOf[Arma]))
  val carrera: Carrera = Carrera(hambreQueGenera = 50, requisitoDeParticipacion = NoRequisito)
  val postas: List[Posta] = List(pesca, combate, carrera)

  val dragones: List[Dragon] = List.fill(3)(FuriaNocturna(peso = 5000.0, danio = 50.0))

  val torneo = TorneoEstandar(postas, dragones)

  "sin ganador" should "no quedan más competidores en pie debido a la posta combate, por lo tanto no hay ganador" in {
    val resultado = torneo(List(Hipo, Hipo, Hipo, Hipo))
    resultado shouldBe Option.empty
  }

  "ganador por último en pie" should "queda uno solo antes de que se terminen las postas, por lo tanto es el ganador" in {
    val resultado = torneo(List(Patan, Hipo))
    resultado shouldBe Option(Patan.aumentarHambre(5.0))
  }

  "ganador por finalización de postas" should "terminan todos y se decide el ganador por la regla" in {
    val astridConEspada: Vikingo = Vikingo(25.0, 65.0, 50.0, 15.0, Option(espada))
    val patanConEspada: Vikingo =  Vikingo(15.0, 90.0, 80.0, 20.0, Option(espada))

    val resultado = torneo(List(patanConEspada, astridConEspada, Patan, Hipo, Astrid, Patapez))
    resultado shouldBe Option(Patan.aumentarHambre(60.0))
  }
}