import entidades.participantes.Vikingo
import entidades.dragones.{Dragon, FuriaNocturna, Gronckle}
import entidades.items.Arma
import entidades.postas.Carrera
import entidades.requisitos.obj.NoRequisito
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Requerimiento3 extends AnyFlatSpec with Matchers {

  val dragonRapido: FuriaNocturna = FuriaNocturna(peso = 50, danio = 100)
  val dragonLento: Gronckle = Gronckle(peso = 500, pesoMaximoVikingo = 100)

  val dragones: List[Dragon] = List(dragonLento, dragonRapido)

  val vikingo: Vikingo = Vikingo(
    velocidad = 10,
    peso = 40,
    barbarosidad = 5,
    porcentajeHambre = 10,
    item = Option(Arma("lanzapiedras", 20))
  )

  val carrera: Carrera = Carrera(
    hambreQueGenera = 5,
    requisitoDeParticipacion = NoRequisito
  )

  "apply" should "usar al dragón más veloz si mejora el rendimiento del vikingo en una carrera" in {
    val resultado: List[Vikingo] = carrera(List(vikingo), dragones)

    resultado should have size 1

    val vikingoResultado = resultado.head

    // Participar como jinete debe aumentar el hambre en 5%
    vikingoResultado.porcentajeHambre shouldBe (15.0 +- 0.0001)
  }
}