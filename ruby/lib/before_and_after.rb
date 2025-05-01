require_relative 'invariants'

module AntesYDespues
    attr_reader :before_procs, :after_procs # Defino getters para poder acceder en el define_method de method_added

    def before_and_after_each_call(before_proc, after_proc)
        # Inicializo los arrays si no existen
        @before_procs ||= []
        @after_procs ||= []

        # Añado los procs al final de las listas (solo si no son nil)
        @before_procs << before_proc
        @after_procs << after_proc
    end

    def method_added(method_name)
        return if @method_added || @before_procs == nil # Evito recursión infinita ya que define_method llama a method_added
        # Por otro lado, no hago nada si no se puso before_and_after_each_call (@before_procs será nil)

        @method_added = true

        metodo_original = instance_method(method_name) # Obtengo el metodo original (unbound method)

        define_method(method_name) do |*args, &block| # Redefino la lógica del metodo. Lleva *args y &block por si el metodo tiene parametros y/o bloque

            self.class.before_procs.each do |before_proc| # Primero se ejecutan los before_procs (debo accederlos desde la clase)
                before_proc.call(self) # Le paso la instancia que lo llamó para invariant
            end

            retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el metodo original (primero lo bindeo a self)

            self.class.after_procs.each do |after_proc| # Luego se ejecutan los after_procs (debo accederlos desde la clase)
                after_proc.call(self) # Le paso la instancia que lo llamó para invariant
            end

            #retorno Mepa que esto esta re de mas xd
        end
        @method_added = false
    end

end