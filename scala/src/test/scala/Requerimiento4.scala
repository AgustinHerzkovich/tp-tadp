import entidades.competidores.Vikingo
import entidades.dragones.*
import entidades.requisitos.{RequisitoCargaMinima, RequisitoItem}
import entidades.torneo.reglas.{Regla, ReglaEstandar}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import entidades.items.{Arma, Comestible, SistemaDeVuelo}
import entidades.torneo.Torneo
import entidades.torneo.postas.{Carrera, Combate, Pesca, Posta}

class Requerimiento4 extends AnyFlatSpec {

  // Ítem requerido por la posta de combate
  val espada = new Arma("Espada Valyria", 40.0)

  // Postas (con hambre y requisitos específicos)
  val pesca = new Pesca(hambreQueGenera = 5, Some(new RequisitoCargaMinima(500)))
  val combate = new Combate(hambreQueGenera = 5, Right(new RequisitoItem(espada)))
  val carrera = new Carrera(hambreQueGenera = 50, None)
  val postas: List[Posta] = List(pesca, combate, carrera)

  // Dragones disponibles (mismo tipo, FuriaNocturna, todos iguales)
  val dragones: List[Dragon] = List(
    new FuriaNocturna(peso = 100.0, danio = 50.0),
    new FuriaNocturna(peso = 100.0, danio = 50.0),
    new FuriaNocturna(peso = 100.0, danio = 50.0)
  )

  // Regla estándar
  val regla = new ReglaEstandar

  // Vikingos
  val hipo = new Vikingo(20.0, 70.0, 30.0, 10.0, Some(new SistemaDeVuelo))
  val astrid = new Vikingo(25.0, 65.0, 50.0, 15.0, Some(new Arma("Hacha Bárbara", 30.0))) // No tiene la espada
  val patan = new Vikingo(15.0, 90.0, 80.0, 20.0, Some(new Arma("Maza Rompecráneos", 100.0))) // Tampoco tiene la espada
  val patapez = new Vikingo(18.0, 60.0, 20.0, 40.0, Some(new Comestible(10))) // Come al final

  // Torneo compartido
  val torneo = new Torneo(postas, dragones, regla)

  "sin ganador" should "no quedan más competidores en pie, por lo tanto no hay ganador" in {
    // Hipo no cumple carga mínima para pesca y no tiene arma para combate.
    val resultado = torneo.realizarTorneo(List(hipo))
    resultado shouldBe None
  }

  "ganador por último en pie" should "queda uno solo antes de que se terminen las postas, por lo tanto es el ganador" in {
    // Solo Patán podrá seguir: él puede montar dragón y no requiere cumplir requisito de arma en pesca ni combate
    val resultado = torneo.realizarTorneo(List(patan, hipo))
    resultado shouldBe Some(patan)
  }

  "ganador por finalización de postas" should "terminan todos y se decide el ganador por la regla" in {
    // Astrid y Patán no cumplen requisito de combate (no tienen la espada requerida).
    // Pero asumimos que en esta versión del test se modifica combate para aceptar sus armas,
    // o que tienen la espada. Así ambos llegan hasta el final.
    val astridConEspada = new Vikingo(25.0, 65.0, 50.0, 15.0, Some(espada))
    val patanConEspada = new Vikingo(15.0, 90.0, 80.0, 20.0, Some(espada))

    val resultado = torneo.realizarTorneo(List(patanConEspada, astridConEspada))

    // El que gane será el que determine la regla
    resultado shouldBe Some(regla.quienGana(List(patanConEspada, astridConEspada)))
  }
}