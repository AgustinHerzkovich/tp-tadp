class Object
    def llamar_lista_procs(lista, method_name, *args) # Lo metemos dentro de Object para que sea accesible desde cualquier instancia
        lista.each do |elemento|
            elemento.call(self, method_name, *args)
        end
    end
end

module BeforeAndAfter
    def before_and_after(method_name, before_procs, after_procs)
        metodo_original = instance_method(method_name) # Obtengo el metodo original (unbound method)

        define_method(method_name) do |*args, &block| # Redefino la lógica del metodo. Lleva *args y &block por si el metodo tiene parametros y/o bloque
            llamar_lista_procs(before_procs, method_name, *args)
            retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el metodo original (primero lo bindeo a self)
            llamar_lista_procs(after_procs, method_name, *args)
            retorno # Se devuelve el valor de retorno del metodo, por si se quiere utilizar
        end
    end
end

module BeforeAndAfterEach
    include BeforeAndAfter
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

        before_and_after(method_name, @before_procs, @after_procs)

        @avoid_recursion = false

        super # Llamo a super por si hay otra redefinicion de method_added
    end
end