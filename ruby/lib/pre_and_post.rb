module PreAndPost
    attr_reader :precondicion, :postcondicion
    def pre(&condicion)
        condicion("precondicion", condicion)
    end

    def post(&condicion)
        condicion("postcondicion", condicion)
    end

    def condicion(nombre, condicion) # Metodo para no repetir logica en pre y post
        proc_condicion = proc do |instancia, method_name, *args|
            #@parametros[method_name].each_with_index do |parametro, index|
            #    binding.local_variable_set(parametro[1], args[index])
            #end
            unless instancia.instance_exec(*args, &condicion)
                raise "Excepcion: El objeto #{instancia} no cumplio con su " + nombre
            end
        end

        variable_instancia = ("@#{nombre}").to_sym

        if instance_variable_get(variable_instancia).nil?
            instance_variable_set(variable_instancia, proc_condicion)
        else
            raise "Excepcion: No podes definir mas de una " + nombre
        end
    end

    def method_added(method_name)
        return if @avoid_recursion

        @avoid_recursion = true

        pre = enlistar(@precondicion)
        post = enlistar(@postcondicion)

        #@parametros ||= {}
        #@parametros[method_name] = instance_method(method_name).parameters

        before_and_after(method_name, pre, post)

        @avoid_recursion = false

        @precondicion = nil
        @postcondicion = nil

        super
    end

    def enlistar(variable)
        variable ? [variable] : []
    end
end