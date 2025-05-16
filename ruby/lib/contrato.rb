class Class
  attr_reader :before_procs, :after_procs # Defino getters para poder acceder en el define_method de method_added

  private # Ponemos privado el metodo para que nadie de afuera pueda redefinir los metodos
  def redefinir_metodo(method_name)
    @avoid_recursion = true

    metodo_original = instance_method(method_name) # Obtengo el metodo original (unbound method)
    define_method(method_name) do |*args, &block|
      self.class.before_procs.each do |before_proc|
        before_proc.call(self, method_name, *args)
      end
      retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el metodo original (primero lo bindeo a self)
      self.class.after_procs.each do |after_proc|
        after_proc.call(self, method_name, *args)
      end
      retorno # Se devuelve el valor de retorno del metodo, por si se quiere utilizar
    end

    @avoid_recursion = false
  end

  #def inherited(padre) # TODO: Verificar que funcione esto para invariante
  #  if padre == Class
  #    redefinir_metodos_existentes
  #  end
  #end

  def redefinir_metodos_existentes
    if @metodos_existentes_redefinidos.nil? # TODO: RARO
      # Ponerle los bloques a los metodos ya existentes (definidos antes del invariant)
      metodos_a_modificar = instance_methods(false)
      metodos_a_modificar << :initialize # Agregamos initialize dado que este no se encuentra en los metodos de instancia de la clase
      metodos_a_modificar.each do |method_name|
        # Por cada simbolo
        redefinir_metodo(method_name)
      end
      @metodos_existentes_redefinidos = true
    end

  end
  public
  def before_and_after_each_call(before_proc, after_proc)
    @before_procs ||= []
    @after_procs ||= []
    # Añado los procs al final de las listas
    @before_procs << before_proc if before_proc
    @after_procs << after_proc if after_proc
    redefinir_metodos_existentes
  end

  def method_added(method_name)
    return if @avoid_recursion # Evito recursión infinita ya que define_method llama a method_added

    if @before_procs == nil && @after_procs == nil  # Por otro lado, no hago nada si no se puso before_and_after_each_call (@before_procs será nil)
      super # Solo llamo a super por si hay otra redefinicion de method_added
      return
    end

    redefinir_metodo(method_name)

    super # Llamo a super por si hay otra redefinicion de method_added
  end

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

    before_and_after_each_call(nil, proc_invariante)
  end

  def pre(&precondicion)
    # TODO
  end

  def post(&postcondicion)
    # TODO
  end
end