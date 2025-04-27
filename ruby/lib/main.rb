require_relative 'before_and_after'
require_relative 'invariants'
require_relative 'pre_and_post'

class Class
    include AntesYDespues
    include Invariante
end

class Guerrero
    before_and_after_each_call(proc {}, proc {puts "Sali"})
    attr_accessor :vida, :fuerza
    # @vida = 100
    def initialize(vida, fuerza)
      @vida = vida
      @fuerza = fuerza
    end
    invariant { @vida >= 0}
    #invariant { fuerza > 0 && fuerza < 100 }

    def atacar(otro)
        otro.vida -= fuerza
    end
end

a = Guerrero.new(0,10)
b = Guerrero.new(0,10)
a.atacar(b)