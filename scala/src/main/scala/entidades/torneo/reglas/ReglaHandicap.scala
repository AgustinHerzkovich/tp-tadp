package entidades.torneo.reglas
import entidades.participantes.Vikingo
class ReglaHandicap extends ReglaEstandar {
  override def ordenDeMonturas(vikingos: List[Vikingo]): List[Vikingo] = vikingos.reverse
}
