module AntesYDespues
    def before_and_after_each_call(before_proc, after_proc)
        @before_procs ||= []
        @after_procs ||= []

        @before_procs << before_proc
        @after_procs << after_proc
    end

    def method_added(method_name)
        return if @mato_recursividad # jonkler func

        metodo = instance_method(method_name)

        @mato_recursividad = true

        define_method(method_name) do |*args, &block|
            self.class.instance_variable_get(:@before_procs)&.each do |before_proc|
                before_proc.call
            end
            retorno = metodo.bind(self).call(*args, &block) # Guardo el valor de retorno
            self.class.instance_variable_get(:@after_procs)&.each do |after_proc|
                after_proc.call
            end
            retorno
        end

        @mato_recursividad = false
    end
end