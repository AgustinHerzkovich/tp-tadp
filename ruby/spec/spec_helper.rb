require 'rspec'
require_relative '../lib/contratos'

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

# Clase de prueba para invariantes
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

# Clase de prueba para precondiciones y postcondiciones
class Operaciones
  #precondición de dividir
  pre { divisor != 0 }
  #postcondición de dividir
  post { |result| result * divisor == dividendo }
  def dividir(dividendo, divisor)
    dividendo / divisor
  end

  # este metodo no se ve afectado por ninguna pre/post condición
  def restar(minuendo, sustraendo)
    minuendo - sustraendo
  end
end

# Clase de prueba integral
class Pila
  attr_accessor :current_node, :capacity

  invariant { capacity >= 0 }

  post { empty? }
  def initialize(capacity)
    @capacity = capacity
    @current_node = nil
  end

  pre { !full? }
  post { height > 0 }
  def push(element)
    @current_node = Node.new(element, current_node)
  end

  pre { !empty? }
  def pop
    element = top
    @current_node = @current_node.next_node
    element
  end

  pre { !empty? }
  def top
    current_node.element
  end

  def height
    empty? ? 0 : current_node.size
  end

  def empty?
    current_node.nil?
  end

  def full?
    height == capacity
  end

  Node = Struct.new(:element, :next_node) do
    def size
      next_node.nil? ? 1 : 1 + next_node.size
    end
  end
end