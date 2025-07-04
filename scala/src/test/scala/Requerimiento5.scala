import entidades.dragones.obj.Chimuelo
import entidades.dragones.{Dragon, FuriaNocturna}
import entidades.participantes.{Equipo, Vikingo}
import entidades.participantes.obj.{Astrid, Hipo, Patan, Patapez}
import entidades.requisitos.obj.NoRequisito
import entidades.torneo.Torneo
import entidades.torneo.postas.Carrera
import entidades.torneo.reglas.*
import entidades.torneo.reglas.obj.{ReglaEquipos, ReglaHandicap, ReglaTorneoInverso}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Requerimiento5 extends AnyFlatSpec with Matchers {

  // Configuración común para todos los tests
  val dragones: List[Dragon] = List.fill(3)(Chimuelo)
  val carreraSimple: Carrera = Carrera(hambre = 5, requisito = NoRequisito)

  // Lista de vikingos para las pruebas
  val participantes: List[Vikingo] = List(Hipo, Astrid, Patan, Patapez)

  "ReglaEstandar" should "eliminar la mitad inferior y elegir al primer vikingo" in {
    val regla = ReglaEstandar()
    val torneo = Torneo(List(carreraSimple), dragones, regla)

    val resultado = torneo(participantes)

    // Hipo debería ganar siendo el primero de la mitad superior, por ser el más rápido
    resultado shouldBe Option(Hipo.aumentarHambre(5.0))
  }

  "ReglaHandicap" should "invertir el orden de monturas y mantener reglas estándar de eliminación" in {
    val regla = ReglaHandicap
    val torneo = Torneo(List(carreraSimple), dragones, regla)

    val resultado = torneo(participantes)

    // Al invertir el orden, Patapez debería montar primero
    resultado shouldBe Option(Patapez.aumentarHambre(5.0))
  }

  "ReglaTorneoInverso" should "mantener la mitad inferior y elegir al último" in {
    val regla = ReglaTorneoInverso
    val torneo = Torneo(List(carreraSimple), dragones, regla)

    val resultado = torneo(participantes)

    // Debería quedarse con Patan y Astrid (mitad inferior) y elegir a Astrid por ser último
    resultado shouldBe Option(Astrid.aumentarHambre(5.0))
  }

  "ReglaEliminacion" should "eliminar una cantidad fija de vikingos" in {
    val regla = ReglaEliminacion(2)
    val torneo = Torneo(List(carreraSimple), dragones, regla)

    val resultado = torneo(participantes)

    // Deberían quedar solo Hipo y Astrid, ganando Hipo
    resultado shouldBe Option(Hipo.aumentarHambre(5.0))
  }

  "ReglaVetoDragones" should "filtrar dragones según el criterio establecido" in {
    // Creamos dragones con diferentes daños
    val dragonesVariados = List(
      FuriaNocturna(peso = 5000, danio = 30),
      FuriaNocturna(peso = 5000, danio = 60),
      FuriaNocturna(peso = 5000, danio = 40)
    )

    val regla = ReglaVetoDragones(_.danio > 50)
    val torneo = Torneo(List(carreraSimple), dragonesVariados, regla)

    val resultado = torneo(participantes)

    // El ganador debería tener el hambre aumentada por participar
    resultado shouldBe Option(Hipo.aumentarHambre(5.0))
  }

  "ReglaEquipos" should "eliminar la mitad de los peores y declarar ganador al equipo con más miembros" in {
    val regla = ReglaEquipos
    val torneo = Torneo(List(carreraSimple), dragones, regla)

    // Asignamos equipo a cada vikingo
    val hipoConEquipo = Hipo.copy(equipo = Option("Equipo Rojo"))
    val astridConEquipo = Astrid.copy(equipo = Option("Equipo Azul"))
    val patanConEquipo = Patan.copy(equipo = Option("Equipo Azul"))
    val patapezConEquipo = Patapez.copy(equipo = Option("Equipo Rojo"))

    // Equipos formados
    val equipoRojo = Equipo(nombre = "Equipo Rojo", miembros = List(hipoConEquipo, patapezConEquipo))
    val equipoAzul = Equipo(nombre = "Equipo Azul", miembros = List(astridConEquipo, patanConEquipo))

    val resultado = torneo(List(equipoRojo, equipoAzul))

    // Gana el Equipo Azul pero solo queda un miembro, Patan
    val equipoEsperado = Equipo(nombre = "Equipo Azul", miembros = List(patanConEquipo.aumentarHambre(5.0)))

    resultado shouldBe Option(equipoEsperado)
  }
}