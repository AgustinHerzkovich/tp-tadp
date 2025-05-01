module PreAndPost
    attr_reader :precondicion, :postcondicion
    def pre(&condicion)
        proc_condicion = proc do |instancia, *args|
            unless instancia.instance_exec(*args, &condicion)
                raise "Excepcion: El objeto #{instancia} no cumplió con su precondicion"
            end
        end
        @precondicion = proc_condicion
    end

    def post(&condicion)
        proc_condicion = proc do |instancia, retorno, *args|
            unless instance.instance_exec(retorno, *args, &condicion)
                raise "Excepcion: El objeto #{instancia} no cumplió con su postcondicion"
            end
        end
        @postcondicion = proc_condicion
    end

    def method_added(method_name)
        return if @avoid_recursion

        @avoid_recursion = true

        metodo_original = instance_method(method_name)

        pre = self.class.precondicion
        post = self.class.postcondicion

        define_method(method_name) do |*args, &block|

            pre.call(self, *args) unless pre.nil?

            retorno = metodo_original.bind(self).call(*args, &block)

            post.call(self, retorno, *args) unless post.nil?

            retorno
        end

        @avoid_recursion = false

        @precondicion = nil
        @postcondicion = nil
    end
end
