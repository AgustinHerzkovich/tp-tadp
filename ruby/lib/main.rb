require_relative 'before_and_after'
require_relative 'invariant'
require_relative 'pre_and_post'

class Class
    include BeforeAndAfter
    include Invariant
    include PreAndPost
end

=begin
# Clase de Prueba de Before y After
class MiClase
    before_and_after_each_call(
        # Bloque Before. Se ejecuta antes de cada mensaje
        proc{ puts 'Entré a un mensaje' },
        # Bloque After. Se ejecuta después de cada mensaje
        proc{ puts 'Salí de un mensaje' }
    )

    def mensaje_1
        puts 'mensaje_1'
        return 5
    end
end

class MiClase2
    before_and_after_each_call(
        # Bloque Before. Se ejecuta antes de cada mensaje
        proc{ puts 'Entré a un mensaje 2' },
        # Bloque After. Se ejecuta después de cada mensaje
        proc{ puts 'Salí de un mensaje 2' }
    )

    def mensaje_1(edad)
        puts 'mensaje_1 ' + edad
        5
    end
end

class MiClase2
    before_and_after_each_call(
        # Bloque Before. Se ejecuta antes de cada mensaje
        proc{ puts 'Entré a un mensaje 3' },
        # Bloque After. Se ejecuta después de cada mensaje
        proc{ puts 'Salí de un mensaje 3' }
    )

    def mensaje_2
        puts 'mensaje_2'
        3
    end

end

MiClase.new.mensaje_1
#MiClase2.new.mensaje_1('50')
#MiClase2.new.mensaje_2
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

a = Guerrero.new(1,10)
b = Guerrero.new(2,10)
a.atacar(b)
=end

# Prueba de pre y post

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

puts Operaciones.new.dividir(4,2)
puts Operaciones.new.dividir(4,0)