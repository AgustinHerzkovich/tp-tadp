=begin
describe MiClase do
    let(:mi_instancia) { MiClase.new }
    it 'pone los procs antes y después del metodo' do
        expect(mi_instancia.mensaje_1).to eq(5)
    end
end


describe Guerrero do
    #let(:prueba) { Prueba.new }
    describe '#materia' do
        it 'debería pasar este test' do
            #expect(prueba.materia).to be :tadp
        end
    end
end

describe PreAndPostTest do
    #let(:prueba) { Prueba.new }
    describe '#materia' do
        it 'debería pasar este test' do
            #expect(prueba.materia).to be :tadp
        end
    end
end
=end

#-------------------

describe MiClase do
    let(:obj) { MiClase.new }

    it 'ejecuta before y after alrededor de mensaje_1' do
        salida = capture_stdout do
            resultado = obj.mensaje_1
            expect(resultado).to eq(5)
        end

        expect(salida).to include("Entré a un mensaje")
        expect(salida).to include("mensaje_1")
        expect(salida).to include("Salí de un mensaje")
    end

    it 'ejecuta before y after alrededor de mensaje_2' do
        salida = capture_stdout do
            resultado = obj.mensaje_2
            expect(resultado).to eq(3)
        end

        expect(salida).to include("Entré a un mensaje")
        expect(salida).to include("mensaje_2")
        expect(salida).to include("Salí de un mensaje")
    end

    it 'usa los nuevos procs en mensaje_3' do
        salida = capture_stdout do
            resultado = obj.mensaje_3
            expect(resultado).to eq(5)
        end

        expect(salida).to include("Entré a un mensaje 2")
        expect(salida).to include("mensaje_1")
        expect(salida).to include("Salí de un mensaje 2")
    end
end

#-------------------

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
        puts 'mensaje_1'
        5
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