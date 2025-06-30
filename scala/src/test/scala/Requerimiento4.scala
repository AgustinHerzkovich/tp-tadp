import entidades.dragones.{Dragon, FuriaNocturna}
import entidades.items.{Arma, Comestible, SistemaDeVuelo}
import entidades.participantes.Vikingo
import entidades.requisitos.obj.NoRequisito
import entidades.requisitos.{RequisitoCargaMinima, RequisitoItem}
import entidades.torneo.Torneo
import entidades.torneo.postas.{Carrera, Combate, Pesca, Posta}
import entidades.torneo.reglas.ReglaEstandar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Requerimiento4 extends AnyFlatSpec with Matchers {

  val espada = new Arma("Espada Valyria", 40.0)

  val pesca = new Pesca(hambre = 5, requisito = new RequisitoCargaMinima(500))
  val combate = new Combate(hambre = 5, requisito = new RequisitoItem(i => i.isInstanceOf[Arma] && i.nombre == "Espada Valyria"))
  val carrera = new Carrera(hambre = 50, requisito = NoRequisito)
  val postas: List[Posta] = List(pesca, combate, carrera)

  val dragones: List[Dragon] = List.fill(3)(new FuriaNocturna(peso = 100.0, danio = 50.0))

  val regla = new ReglaEstandar

  val hipo = new Vikingo(20.0, 70.0, 30.0, 10.0, Option(new SistemaDeVuelo(nombre = "aerolineas argentinas")))
  val astrid = new Vikingo(25.0, 65.0, 50.0, 15.0, Option(new Arma("Hacha Bárbara", 30.0)))
  val patan = new Vikingo(15.0, 90.0, 80.0, 20.0, Option(new Arma("Maza Rompecráneos", 100.0)))
  val patapez = new Vikingo(18.0, 60.0, 20.0, 40.0, Option(new Comestible("manzana de notch", 10)))

  val torneo = new Torneo(postas, dragones, regla)

  "sin ganador" should "no quedan más competidores en pie, por lo tanto no hay ganador" in {
    val resultado = torneo(List(hipo)) // usar apply
    resultado shouldBe Option.empty
  }

  "ganador por último en pie" should "queda uno solo antes de que se terminen las postas, por lo tanto es el ganador" in {
    val resultado = torneo(List(patan, hipo))
    resultado shouldBe Option(patan)
  }

  "ganador por finalización de postas" should "terminan todos y se decide el ganador por la regla" in {
    val astridConEspada = new Vikingo(25.0, 65.0, 50.0, 15.0, Option(espada))
    val patanConEspada = new Vikingo(15.0, 90.0, 80.0, 20.0, Option(espada))

    val resultado = torneo(List(patanConEspada, astridConEspada))
    resultado shouldBe Option(regla.quienGana(List(patanConEspada, astridConEspada)))
  }
}