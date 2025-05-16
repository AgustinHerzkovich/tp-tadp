require 'rspec'
require_relative '../lib/contrato'

# Clase de Prueba de Before y After
class MiClase
  before_and_after_each_call(
    # Bloque Before. Se ejecuta antes de cada mensaje
    proc { puts "Entré a un mensaje" },
    # Bloque After. Se ejecuta después de cada mensaje
    proc { puts "Salí de un mensaje" }
  )

  def mensaje_1
    puts "mensaje_1"
    return 5
  end

  def mensaje_2
    puts "mensaje_2"
    return 3
  end

end

# Clase de Prueba de Invariants
class Guerrero
  attr_accessor :vida, :fuerza

  def initialize(vida, fuerza)
    @vida = vida
    @fuerza = fuerza
  end

  invariant { vida >= 0 }
  invariant { fuerza > 0 && fuerza < 100 }

  def atacar(otro)
    otro.vida -= fuerza
  end
end