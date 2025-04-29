require_relative 'invariants'

module AntesYDespues
    def before_and_after_each_call(before_proc, after_proc)
        # Inicializo los arrays si no existen
        @before_procs ||= []
        @after_procs ||= []

        # Añado los procs al final de las listas (solo si no son nil)
        @before_procs << before_proc
        @after_procs << after_proc
    end

    def method_added(method_name)
        return if @method_added # Evito recursión infinita ya que define_method llama a method_added
        @method_added = true

        metodo_original = self.instance_method(method_name) # Obtengo el método original (unbound method)

        define_method(method_name) do |*args, &block| # Redefino la lógica del método
            self.class.instance_variable_get(:@before_procs)&.each do |before_proc| # Primero se ejecutan los before_procs
                before_proc.call(self,args) # Nuevo
            end

            retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el método original (primero lo bindeo a self)

            self.class.instance_variable_get(:@after_procs)&.each do |after_proc| # Luego se ejecutan los after_procs
                after_proc.call(self,args) # Nuevo
            end

            retorno
        end
        @method_added = false
    end

end