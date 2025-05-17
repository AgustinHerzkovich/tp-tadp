# Test Before And After Each Call
describe MiClase do
    describe "#mensaje_1" do
        it "Se ejecuta el proc inicial, luego mensaje_1 y finalmente el proc final" do
            a = MiClase.new
            salida_esperada = "Entré a un mensaje\nmensaje_1\nSalí de un mensaje\n"
            expect{(a.mensaje_1)}.to output(salida_esperada).to_stdout
        end
    end

    describe "#mensaje_3" do
        it "Se reabre la clase y se agregan nuevos procs para antes y después" do
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

            a = MiClase.new
            salida_esperada = "Entré a un mensaje\nEntré a un mensaje 2\nmensaje_3\nSalí de un mensaje\nSalí de un mensaje 2\n"
            expect{(a.mensaje_3)}.to output(salida_esperada).to_stdout
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

# Test Pre and Post
describe Operaciones do
    describe "#dividir" do
        it "Se divide 4 por 2 correctamente" do
            operacion = Operaciones.new
            expect(operacion.dividir(4, 2)).to eq(2)
        end

        it "Se intenta dividir 4 por 0 y se lanza una excepción por precondición no cumplida" do
            operacion = Operaciones.new
            expect{operacion.dividir(4, 0)}.to raise_error(RuntimeError)
        end
    end

    describe "#restar" do
        it "El método restar no se ve afectado por las precondiciones y postcondiciones de dividir" do
            operacion = Operaciones.new
            expect(operacion.restar(5, 0)).to eq(5)
        end
    end
end

# Test Integral
describe Pila do
    describe "Funcionamiento Normal" do
        it "Se cumplen todas las invariantes, precondiciones y postcondiciones"do
            pila = Pila.new(3)

            # Invariante: capacidad no negativa
            expect(pila.capacity).to be >= 0

            # Post de initialize: pila vacía
            expect(pila.empty?).to be true
            expect(pila.height).to eq(0)
            expect(pila.full?).to be false

            # Push 1
            pila.push("a")
            expect(pila.height).to eq(1)          # post: height > 0
            expect(pila.top).to eq("a")
            expect(pila.empty?).to be false
            expect(pila.full?).to be false

            # Push 2
            pila.push("b")
            expect(pila.height).to eq(2)
            expect(pila.top).to eq("b")
            expect(pila.full?).to be false

            # Push 3
            pila.push("c")
            expect(pila.height).to eq(3)
            expect(pila.top).to eq("c")
            expect(pila.full?).to be true         # full? == true al llegar a capacidad

            # Pop 1
            expect(pila.pop).to eq("c")
            expect(pila.height).to eq(2)
            expect(pila.top).to eq("b")
            expect(pila.full?).to be false

            # Pop 2
            expect(pila.pop).to eq("b")
            expect(pila.height).to eq(1)
            expect(pila.top).to eq("a")

            # Pop 3
            expect(pila.pop).to eq("a")
            expect(pila.height).to eq(0)
            expect(pila.empty?).to be true
        end
    end

    describe "#initialize" do
        it "Se intenta inicializar una pila con capacity negativa y tira excepción" do
            expect{Pila.new(-1)}.to raise_error(RuntimeError)
        end
    end

    describe "#pop" do
        it "Se intenta hacer pop de una pila vacía, lo cual tira excepción porque no se cumple la precondición !empty" do
            expect{Pila.new(5).pop}.to raise_error(RuntimeError)
        end
    end

    describe "#top" do
        it "Se intenta obtener el top de una pila vacía, lo cual tira excepción porque no se cumple la precondición !empty" do
            expect{Pila.new(5).top}.to raise_error(RuntimeError)
        end
    end

    describe "#push" do
        it "Se intenta pushear un elemento pero la capacidad de la pila es 0, entonces tira excepción de precondición" do
            expect{Pila.new(0).push(nil)}.to raise_error(RuntimeError)
        end

        it "Se redefine push, se intenta pushear un elemento y no se cumple la postcondición de height > 0, lo cual arroja excepción" do
            class Pila
                def push(element)
                    puts "nada"
                end
            end
            expect{Pila.new(5).push(nil)}.to raise_error(RuntimeError)
        end
    end

    describe "#initialize" do
        it "Se redefine initialize, se intenta inicializar una pila con un current_node != nil y tira excepción" do
            class Pila
                def initialize(capacity)
                    @capacity = capacity
                    @current_node = Node.new(nil, current_node)
                end
            end
            expect{Pila.new(0)}.to raise_error(RuntimeError)
        end
    end
end