package entidades.competidores

import entidades.dragones.Dragon
import entidades.items.Item
import entidades.torneo.postas.Posta

import scala.util.{Failure, Success, Try}


case class Vikingo(
    velocidad: Double,
    peso : Double,
    barbarosidad: Double,
    porcentajeHambre: Double, // tiene sentido que este en el constructor?
    item : Option[Item] = None
  ) extends Competidor {

  val porcentajeHambreMaximo = 100

  override def danio : Double = {
    this.barbarosidad + this.item.map(x =>x.danioItem).getOrElse(0.0)
  }

  override def cargaMaxima : Double = {
    peso * 0.5 + 2 * barbarosidad
  }

  override def aumentarHambre(porcentaje: Double): Vikingo = this.copy(porcentajeHambre = this.porcentajeHambre + porcentaje)

  override def disminuirHambre(porcentaje: Double): Vikingo = this.copy(porcentajeHambre = this.porcentajeHambre - porcentaje)

  def postParticipar(): Vikingo = this // Devuelve el vikingo sin cambios, luego Patapez overridea

  def montar(dragon: Dragon): Try[Jinete] = {
      Try(Jinete(vikingo = this, dragon = dragon))
  }

  def puedeSeguir(): Boolean = porcentajeHambre <= porcentajeHambreMaximo

}
