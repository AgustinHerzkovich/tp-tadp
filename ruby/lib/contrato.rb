# DECISIÓN DE DISEÑO: Se van a redefinir todos los métodos QUE EL USUARIO DEFINA (ej: si quiere redefinir initialize, debe redefinirlo él)
# DECISIÓN DE DISEÑO: Si pones varias precondiciones o postcondiciones, queda la ultima que definiste

class Class
  attr_reader :before_procs, :after_procs # Defimos getters para poder acceder en el define_method

  private # Ponemos privados los metodos auxiliares para que nadie de afuera pueda redefinir los metodos
  def redefinir_metodo(method_name)
    @avoid_recursion = true # Evito recursión infinita ya que define_method llama a method_added

    metodo_original = instance_method(method_name) # Obtengo el metodo original (unbound method)
    define_method(method_name) do |*args, &block|
      self.class.before_procs.each do |before_proc|
        before_proc.call(self, method_name, nil, *args)
      end
      retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el metodo original (primero lo bindeo a self)
      self.class.after_procs.each do |after_proc|
        after_proc.call(self, method_name, retorno, *args)
      end
      retorno # Se devuelve el valor de retorno del metodo, por si se quiere utilizar
    end

    @avoid_recursion = false
  end

  def redefinir_metodos_existentes
    # Ponerle los bloques a los metodos ya existentes (definidos antes del invariant)
    if @metodos_existentes_redefinidos.nil? # SI YA SE REDIFINIERON, NO HACE FALTA REHACERLO!

      # Por cada metodo de la instancia publico o protegido (incluso heredados), veo si estan definidos en ESTA clase
      metodos_publicos_definidos = instance_methods(true).filter { |metodo| method_defined?(metodo,false) }

      # Lo mismo para los privados
      metodos_privados_definidos = private_instance_methods(true).filter { |metodo| private_method_defined?(metodo, false) }

      metodos_a_modificar = metodos_publicos_definidos + metodos_privados_definidos # Union

      metodos_a_modificar.each do |method_name|
        # Por cada metodo, lo redefino
        redefinir_metodo(method_name)
      end
      @metodos_existentes_redefinidos = true
    end
  end

  def construir_contexto(nombre_metodo, resultado = nil, *args)
    contexto = Object.new # Se crea el contexto que es un singleton object
    @parametros[nombre_metodo].each_with_index do |parametro, indice| # Por cada uno de los parámetros
      contexto.define_singleton_method(parametro) {args[indice]} # Se crea un singleton method en el objeto, el cual devuelve el valor que se pasó como argumento
    end

    if resultado # Si se pasa un resultado, en el caso de postcondiciones
      contexto.define_singleton_method(:resultado) {resultado} # Se crea un singleton method, que retorna ese resultado
    end

    contexto
  end

  public
  def before_and_after_each_call(before_proc, after_proc) # Se puede pasar un metodo como parametro para solo redefinir ese metodo, si no se pasa se redefinen todos
    # Lazy initialization
    @before_procs ||= []
    @after_procs ||= []
    # Añado los procs al final de las listas (a no ser que se pase nil)
    @before_procs << before_proc if before_proc
    @after_procs << after_proc if after_proc

    redefinir_metodos_existentes
  end

  def method_added(method_name)
    return if @avoid_recursion # Evito recursión infinita ya que define_method llama a method_added

    if @precondicion || @postcondicion
      @parametros ||= {} # Si no existe, creo un hash del tipo metodo => parámetros
      @parametros[method_name] = instance_method(method_name).parameters.map(&:last) # Almacenamos los nombres de los parametros del metodo

      before_and_after_each_call(@precondicion, nil)
      before_and_after_each_call(nil, @postcondicion)

      @precondicion = nil # Resetea la variable
      @postcondicion = nil # Resetea la variable

    end

    if @before_procs.nil?  # Por otro lado, no hago nada si no se hizo before_and_after_each_call (@before_procs será nil)
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
    proc_precondicion = proc do |instancia, metodo, resultado, *args| # Ponemos resultado aunque no se use por postcondicion
      if metodo == @ultimo_metodo_con_precondicion || @ultimo_metodo_con_precondicion == nil # Vemos el nombre del ultimo metodo agregado con precondicion
        @ultimo_metodo_con_precondicion = metodo # Seteamos el nombre del metodo que se llamo
        contexto = construir_contexto(metodo, resultado, *args)
        unless contexto.instance_exec(&precondicion)
          raise "Excepcion: El objeto #{instancia} no cumplio su precondicion"
        end
      end
    end

    @precondicion = proc_precondicion
  end

  def post(&postcondicion)
    proc_postcondicion = proc do |instancia, metodo, resultado, *args|
      if metodo == @ultimo_metodo_con_postcondicion || @ultimo_metodo_con_postcondicion == nil # Vemos el nombre del ultimo metodo agregado con postcondicion
        @ultimo_metodo_con_postcondicion = metodo # Seteamos el nombre del metodo que se llamo
        contexto = construir_contexto(metodo, resultado, *args)
        unless contexto.instance_exec(resultado, &postcondicion)
          raise "Excepcion: El objeto #{instancia} no cumplio su postcondicion"
        end
      end
    end

    @postcondicion = proc_postcondicion
  end
end
