package entidades.competidores

import entidades.items.Item
import entidades.torneo.postas.{Carrera, Combate, Pesca, Posta}

trait Competidor() {

  def velocidad: Double

  def peso : Double

  def barbarosidad: Double

  def porcentajeHambre: Double

  def item: Option[Item]

  def danio: Double

  def cargaMaxima: Double

  def aumentarHambre(porcentaje: Double): Competidor

  def disminuirHambre(porcentaje: Double): Competidor

  def esMejorQue(competidor: Competidor): Posta => Boolean = {
    case _: Combate => this.danio >= competidor.danio
    case _: Pesca => this.cargaMaxima >= competidor.cargaMaxima
    case _: Carrera => this.velocidad >= competidor.velocidad
  }
  
  def puedeSeguir(): Boolean
  
}