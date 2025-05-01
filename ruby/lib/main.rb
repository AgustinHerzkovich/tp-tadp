require_relative 'before_and_after'
require_relative 'invariants'
require_relative 'pre_and_post'

class Class
    include BeforeAndAfter
    include Invariant
end

=begin
# Prueba de before y after
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

=begin
# Prueba de invariantes
class Guerrero
    attr_accessor :vida, :fuerza

    def initialize(vida, fuerza)
      @vida = vida
      @fuerza = fuerza
    end

    #invariant { fuerza > 0 && fuerza < 100 }
    invariant { vida >= 0 }

    def atacar(otro)
        otro.vida -= fuerza
        #otro.recibir_danio(fuerza) # Hago esto en vez de otro.vida -= fuerza pq no toma los accessors :(
    end

    def recibir_danio(danio)
        self.vida -= danio
    end
end

a = Guerrero.new(-10,10)
b = Guerrero.new(0,10)
a.atacar(b)
=end