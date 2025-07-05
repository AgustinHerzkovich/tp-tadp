package entidades.participantes

import entidades.items.Item
import entidades.postas.{Carrera, Combate, Pesca, Posta}

trait Individuo() {

  def velocidad: Double

  def peso : Double

  def barbarosidad: Double

  def porcentajeHambre: Double

  def item: Option[Item]

  def danio: Double

  def cargaMaxima: Double

  def aumentarHambre(porcentaje: Double): Individuo

  def disminuirHambre(porcentaje: Double): Individuo

  def esMejorQue(individuo: Individuo): Posta => Boolean = { // Miro el tipo de posta para saber si es mejor que el otro individuo
    case _: Combate => this.danio >= individuo.danio
    case _: Pesca => this.cargaMaxima >= individuo.cargaMaxima
    case _: Carrera => this.velocidad >= individuo.velocidad
  }
  
  def estaHambriento(): Boolean
  
  def porcentajeHambreMaximo: Double
}