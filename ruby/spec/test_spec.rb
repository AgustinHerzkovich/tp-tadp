# Test before and after each call
describe MiClase do
    describe "#mensaje_1" do
        it 'ejecuta before y after alrededor de mensaje_1' do
            obj = MiClase.new
            salida_esperada = "Entré a un mensaje\nmensaje_1\nSalí de un mensaje\n"
            expect{obj.mensaje_1}.to output(salida_esperada).to_stdout
        end
    end

    describe "#mensaje_2" do
        it 'ejecuta before y after alrededor de mensaje_2' do
            obj = MiClase.new
            salida_esperada = "Entré a un mensaje\nmensaje_2\nSalí de un mensaje\n"
            expect{obj.mensaje_2}.to output(salida_esperada).to_stdout
        end
    end

    describe "#mensaje_3" do
        it "ejecuta el segundo before y after alrededor de mensaje_3, prueba open classes" do
            # Reabrimos la clase
            class MiClase
                before_and_after_each_call(
                  # Bloque Before. Se ejecuta antes de cada mensaje
                  proc { puts "Entré a un mensaje 2" },
                  # Bloque After. Se ejecuta después de cada mensaje
                  proc { puts "Salí de un mensaje 2" }
                )

                def mensaje_3
                    puts "mensaje_3"
                    5
                end
            end

            salida_esperada_1 = "Entré a un mensaje\nEntré a un mensaje 2\nmensaje_1\nSalí de un mensaje\nSalí de un mensaje 2\n"
            salida_esperada_3 = "Entré a un mensaje\nEntré a un mensaje 2\nmensaje_3\nSalí de un mensaje\nSalí de un mensaje 2\n"
            obj = MiClase.new
            expect{obj.mensaje_1}.to output(salida_esperada_1).to_stdout
            expect{obj.mensaje_3}.to output(salida_esperada_3).to_stdout
        end
    end
end

# Test invariants
describe Guerrero do
    describe "#initialize" do
        it "No se puede inicializar un guerrero con fuerza negativa" do
            expect{Guerrero.new(10, -1)}.to raise_error(RuntimeError)
        end
    end

    describe "#atacar" do
        it "Un guerrero no puede quedar con vida negativa" do
            a = Guerrero.new(1, 10)
            b = Guerrero.new(11,10)
            expect{b.atacar(a)}.to raise_error(RuntimeError)
        end
    end

    describe "#atacar" do
        it "Los guerreros se atacan correctamente y sus invariantes se cumplen" do
            a = Guerrero.new(11, 10)
            b = Guerrero.new(11,10)
            a.atacar(b)
            b.atacar(a)
            expect(a.vida).to eq(1)
            expect(b.vida).to eq(1)
        end
    end
end