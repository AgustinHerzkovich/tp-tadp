module BeforeAndAfter
    attr_reader :before_procs, :after_procs # Defino getters para poder acceder en el define_method de method_added

    def before_and_after_each_call(before_proc, after_proc)
        # Inicializo los arrays si no existen
        @before_procs ||= []
        @after_procs ||= []

        # Añado los procs al final de las listas
        @before_procs << before_proc
        @after_procs << after_proc
    end

    def method_added(method_name)
        return if @avoid_recursion # Evito recursión infinita ya que define_method llama a method_added

        if @before_procs.nil? # Por otro lado, no hago nada si no se puso before_and_after_each_call (@before_procs será nil)
            super # Solo llamo a super por si hay otra redefinicion de method_added
            return
        end

        @avoid_recursion = true

        metodo_original = instance_method(method_name) # Obtengo el metodo original (unbound method)

        define_method(method_name) do |*args, &block|
            # Redefino la lógica del metodo. Lleva *args y &block por si el metodo tiene parametros y/o bloque

            self.class.before_procs.each do |before_proc|
                # Primero se ejecutan los before_procs (debo accederlos desde la clase)
                before_proc.call(self, method_name) # Le paso la instancia que lo llamó para invariant
            end

            retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el metodo original (primero lo bindeo a self)

            self.class.after_procs.each do |after_proc|
                # Luego se ejecutan los after_procs (debo accederlos desde la clase)
                after_proc.call(self, method_name) # Le paso la instancia que lo llamó para invariant
            end

            retorno # Se devuelve el valor de retorno del metodo, por si se quiere utilizar
        end
        @avoid_recursion = false

        super # Llamo a super por si hay otra redefinicion de method_added
    end
end