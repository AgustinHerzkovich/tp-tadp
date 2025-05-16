require_relative 'before_and_after'
#require_relative 'invariant'
#require_relative 'pre_and_post'

class Class
    include BeforeAndAfterEach
    #include Invariant
    #include PreAndPost
end

=begin
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

# La reabrimos
class MiClase
    before_and_after_each_call(
        # Bloque Before. Se ejecuta antes de cada mensaje
        proc { puts 'Entré a un mensaje 2' },
        # Bloque After. Se ejecuta después de cada mensaje
        proc { puts 'Salí de un mensaje 2' }
    )

    def mensaje_3
        puts 'mensaje_3'
        5
    end
end
MiClase.new.mensaje_1
MiClase.new.mensaje_2
MiClase.new.mensaje_3
=end

# Prueba de invariantes
=begin
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

class Guerrero
    attr_accessor :tonto

    def initialize(vida, fuerza, tonto)
        @vida = vida
        @fuerza = fuerza
        @tonto = tonto
    end

    invariant {deLaMatanza == true}

end

a = Guerrero.new(1, 0, true)
b = Guerrero.new(11,10, true)
a.atacar(b)
a.atacar(b)
=end
# Prueba de pre y post

=begin
class Operaciones
    #precondición de dividir
    pre { divisor != 0 }
    #postcondición de dividir
    #post { |result| result * divisor == dividendo }

    def dividir(dividendo, divisor)
        dividendo / divisor
    end


    # este metodo no se ve afectado por ninguna pre/post condición
    def restar(minuendo, sustraendo)
        minuendo - sustraendo
    end

end

puts Operaciones.new.dividir(4, 2)
puts Operaciones.new.dividir(4,0)
=end