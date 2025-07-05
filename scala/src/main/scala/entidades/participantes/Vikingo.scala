package entidades.participantes

import entidades.dragones.Dragon
import entidades.items.Item
import entidades.postas.Posta

import scala.util.{Failure, Success, Try}


case class Vikingo(
    velocidad: Double,
    peso: Double,
    barbarosidad: Double,
    porcentajeHambre: Double,
    item: Option[Item] = Option.empty,
    equipo: Option[Equipo] = Option.empty,
    nombre: String = "Vikingo"
  ) extends Individuo, Participante {

  // Daño que puede causar, depende de su barbarosidad más el daño del ítem si lo tiene
  override def danio : Double = {
    this.barbarosidad + this.item.map(x =>x.danioItem).getOrElse(0.0)
  }

  override def cargaMaxima : Double = {
    peso * 0.5 + 2 * barbarosidad
  }

  override def aumentarHambre(porcentaje: Double): Vikingo = this.copy(porcentajeHambre = this.porcentajeHambre + porcentaje)

  override def disminuirHambre(porcentaje: Double): Vikingo = this.copy(porcentajeHambre = this.porcentajeHambre - porcentaje)

  // Acción por defecto luego de participar en una posta (puede ser overrideado por otras subclases como Patapez)
  def accionLuegoDeParticiparEnPosta(): Vikingo = this

  // Intenta montar un dragón. Si falla algún requisito, lanza una excepción.
  def montar(dragon: Dragon): Try[Jinete] = {
      Try(Jinete(vikingo = this, dragon = dragon))
  }

  def estaHambriento(): Boolean = porcentajeHambre >= porcentajeHambreMaximo

  override def porcentajeHambreMaximo: Double = 100.0
}