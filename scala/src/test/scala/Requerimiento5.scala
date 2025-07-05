import entidades.dragones.obj.Chimuelo
import entidades.dragones.{Dragon, FuriaNocturna}
import entidades.participantes.{Equipo, Vikingo}
import entidades.participantes.obj.{Astrid, Hipo, Patan, Patapez}
import entidades.postas.Carrera
import entidades.requisitos.obj.NoRequisito
import entidades.torneo.{TorneoEliminacion, TorneoEquipos, TorneoEstandar, TorneoHandicap, TorneoInverso, TorneoVetoDragones}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Requerimiento5 extends AnyFlatSpec with Matchers {

  // Configuración común para todos los tests
  val dragones: List[Dragon] = List.fill(3)(Chimuelo)
  val carreraSimple: Carrera = Carrera(hambreQueGenera = 5, requisitoDeParticipacion = NoRequisito)

  // Lista de vikingos para las pruebas
  val participantes: List[Vikingo] = List(Hipo, Astrid, Patan, Patapez)

  "TorneoEstandar" should "eliminar la mitad inferior y elegir al primer vikingo" in {
    val torneo = TorneoEstandar(List(carreraSimple), dragones)

    val resultado = torneo(participantes)

    // Hipo debería ganar siendo el primero de la mitad superior, por ser el más rápido
    resultado shouldBe Option(Hipo.aumentarHambre(5.0))
  }

  "TorneoHandicap" should "invertir el orden de monturas y mantener reglas estándar de eliminación" in {
    val torneo = TorneoHandicap(List(carreraSimple), dragones)

    val resultado = torneo(participantes)

    // Al invertir el orden, Patapez debería montar primero
    resultado shouldBe Option(Patapez.aumentarHambre(5.0))
  }

  "TorneoInverso" should "mantener la mitad inferior y elegir al último" in {
    val torneo = TorneoInverso(List(carreraSimple), dragones)

    val resultado = torneo(participantes)

    // Debería quedarse con Patan y Astrid (mitad inferior) y elegir a Astrid por ser último
    resultado shouldBe Option(Astrid.aumentarHambre(5.0))
  }

  "TorneoEliminacion" should "eliminar una cantidad fija de vikingos" in {
    val torneo = TorneoEliminacion(List(carreraSimple), dragones, 2)

    val resultado = torneo(participantes)

    // Deberían quedar solo Hipo y Astrid, ganando Hipo
    resultado shouldBe Option(Hipo.aumentarHambre(5.0))
  }

  "TorneoVetoDragones" should "filtrar dragones según el criterio establecido" in {
    // Creamos dragones con diferentes daños
    val dragonesVariados = List(
      FuriaNocturna(peso = 5000, danio = 30),
      FuriaNocturna(peso = 5000, danio = 60),
      FuriaNocturna(peso = 5000, danio = 40)
    )

    val torneo = TorneoVetoDragones(List(carreraSimple), dragonesVariados, _.danio > 50)

    val resultado = torneo(participantes)

    // El ganador debería tener el hambre aumentada por participar
    resultado shouldBe Option(Hipo.aumentarHambre(5.0))
  }

  "TorneoEquipos" should "eliminar la mitad de los peores y declarar ganador al equipo con más miembros" in {
    val torneo = TorneoEquipos(List(carreraSimple), dragones)

    // Creamos equipos
    lazy val equipoRojo1: Equipo = Equipo(nombre = "Equipo Rojo", vikingos = List(hipoConEquipo, patapezConEquipo, hipoConEquipo, patapezConEquipo))
    lazy val equipoAzul: Equipo = Equipo(nombre = "Equipo Azul", vikingos = List(astridConEquipo, patanConEquipo))
    lazy val equipoRojo2: Equipo = Equipo(nombre = "Equipo Rojo", vikingos = List(hipoConEquipo, patapezConEquipo))

    // Reasignamos vikingos con su equipo correspondiente
    lazy val hipoConEquipo = Hipo.copy(equipo = Option(equipoRojo1))
    lazy val patapezConEquipo = Patapez.copy(equipo = Option(equipoRojo1))
    lazy val astridConEquipo = Astrid.copy(equipo = Option(equipoAzul))
    lazy val patanConEquipo = Patan.copy(equipo = Option(equipoAzul))

    val resultado1 = torneo(List(equipoRojo1, equipoAzul))
    val resultado2 = torneo(List(equipoRojo2, equipoAzul))

    // Verificamos que el equipo ganador es el rojo por tener más miembros
    resultado1.get.nombre shouldBe "Equipo Rojo" // Unused expression without side effects ??

    // Verificamos que el equipo ganador es el azul por desempate
    resultado2.get.nombre shouldBe "Equipo Azul"
  }
}