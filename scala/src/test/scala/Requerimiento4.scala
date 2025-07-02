import entidades.dragones.{Dragon, FuriaNocturna}
import entidades.items.Arma
import entidades.participantes.Vikingo
import entidades.participantes.obj.{Hipo, Patan}
import entidades.requisitos.obj.NoRequisito
import entidades.requisitos.{RequisitoCargaMinima, RequisitoItem}
import entidades.torneo.Torneo
import entidades.torneo.postas.{Carrera, Combate, Pesca, Posta}
import entidades.torneo.reglas.ReglaEstandar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Requerimiento4 extends AnyFlatSpec with Matchers {

  val espada = new Arma("Espada Valyria", 40.0)

  val pesca: Pesca = Pesca(hambre = 5, requisito = new RequisitoCargaMinima(200))
  val combate: Combate = Combate(hambre = 5, requisito = new RequisitoItem(i => i.isInstanceOf[Arma]))
  val carrera: Carrera = Carrera(hambre = 50, requisito = NoRequisito)
  val postas: List[Posta] = List(pesca, combate, carrera)

  val dragones: List[Dragon] = List.fill(3)(FuriaNocturna(peso = 5000.0, danio = 50.0))

  val regla = new ReglaEstandar

  val torneo = new Torneo(postas, dragones, regla)

  "sin ganador" should "no quedan más competidores en pie, por lo tanto no hay ganador" in {
    val resultado = torneo(List(Hipo))
    resultado shouldBe Option.empty
  }

  "ganador por último en pie" should "queda uno solo antes de que se terminen las postas, por lo tanto es el ganador" in {
    val resultado = torneo(List(Patan, Hipo))
    resultado shouldBe Option(Patan)
  }

  "ganador por finalización de postas" should "terminan todos y se decide el ganador por la regla" in {
    val astridConEspada: Vikingo = Vikingo(25.0, 65.0, 50.0, 15.0, Option(espada))
    val patanConEspada: Vikingo =  Vikingo(15.0, 90.0, 80.0, 20.0, Option(espada))

    val resultado = torneo(List(patanConEspada, astridConEspada))
    resultado shouldBe Option(patanConEspada.aumentarHambre(60.0))
  }
}