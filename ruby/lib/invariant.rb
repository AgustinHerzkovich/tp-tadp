require_relative 'before_and_after'

module Invariant
    def invariant(&invariante)
        proc_invariante = proc do |instancia, method_name|
            # Añado el parametro "instancia" al bloque para saber en que contexto ejecutar el bloque, y el nombre del metodo pora matar la recursividad
            ultimo_metodo = instancia.instance_variable_get(:@ultimo_metodo) # Leemos el nombre del ultimo metodo que se llamo (si es el primero, sera nil)
            unless ultimo_metodo == method_name # Evitamos recursividad por si el bloque llama un getter
                instancia.instance_variable_set(:@ultimo_metodo, method_name) # Seteamos el nombre del metodo que se llamo
                unless instancia.instance_exec(&invariante)
                    raise "Excepcion: El objeto #{instancia} quedo con estado invalido. Ultimo metodo ejecutado: #{method_name}"
                end
            end
        end

        # Ponerle los bloques a los metodos ya existentes (definidos antes del invariant)
        metodos_a_modificar = instance_methods(false)
        metodos_a_modificar << :initialize # Agregamos initialize dado que este no se encuentra en los metodos de instancia de la clase
        metodos_a_modificar.each do |method_name|
            # Por cada simbolo
            metodo_original = instance_method(method_name) #  Obtengo el unbound method
            @avoid_recursion = true # Evito recursividad del define_method para los invariants
            define_method(method_name) do |*args, &block|
                retorno = metodo_original.bind(self).call(*args, &block)
                proc_invariante.call(self, method_name) # Llamo al proc, pasandole también el nombre del metodo para evitar recursividad
                retorno
            end
            @avoid_recursion = false
        end

        # Ponerle los bloques a los nuevos metodos
        before_and_after_each_call(proc {}, proc_invariante)
    end
end