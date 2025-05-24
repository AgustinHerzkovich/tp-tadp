# DECISIÓN DE DISEÑO: Se van a redefinir todos los métodos que el usuario defina (ej: si quiere redefinir initialize, debe redefinirlo él)
# DECISIÓN DE DISEÑO: Si se definen varias precondiciones y/o postcondiciones para un mismo metodo, sólo se tiene en cuenta la última
# DECISIÓN DE DISEÑO: No se puede poner efecto en las precondiciones y/o postcondiciones, porque puede en algunos casos modificar el objeto original (ej: modificar lista)

class Class
  include Contratos
end

module Contratos
  attr_reader :before_procs, :after_procs # Defimos getters de los procs para poder acceder en el define_method
  
  public
  def before_and_after_each_call(before_proc, after_proc)
    # Lazy initialization
    @before_procs ||= []
    @after_procs ||= []
    # Añado los procs al final de las listas (solo si estos no son nil)
    @before_procs << before_proc if before_proc
    @after_procs << after_proc if after_proc
    
    redefinir_metodos_existentes
  end
  
  def method_added(method_name)
    return if @avoid_recursion # Evito recursión infinita ya que define_method llama a method_added
    
    if @precondicion || @postcondicion
      @parametros ||= {} # Si no existe, creo un hash del tipo metodo => parámetros
      @parametros[method_name] = instance_method(method_name).parameters.map(&:last) # Almacenamos los nombres de los parámetros del metodo
    end
    
    if @precondicion
      precondicion_local = @precondicion # Me guardo el proc de la precondición en una variable local, porque después se resetea la variable
      proc_precondicion = proc do |instancia, metodo, resultado, *args| # Creo un nuevo proc personalizado
        if metodo == method_name # El cual solo tiene contenido, si el metodo coincide con el último metodo definido
          contexto = construir_contexto(instancia, metodo, resultado, *args)
          unless contexto.instance_exec(&precondicion_local) # Hacemos que ese proc se ejecute en el contexto creado y lanza excepción en caso de no cumplirse la condición
            raise "Excepción: El objeto #{instancia} no cumplio su precondición"
          end
        end
      end
      
      before_and_after_each_call(proc_precondicion, nil)
      @precondicion = nil # Resetea la variable
    end
    
    if @postcondicion
      postcondicion_local = @postcondicion # Me guardo el proc de la postcondición en una variable local, porque después se resetea la variable
      proc_postcondicion = proc do |instancia, metodo, resultado, *args| # Creo un nuevo proc personalizado
        if metodo == method_name # El cual solo tiene contenido, si el metodo coincide con el último metodo definido
          contexto = construir_contexto(instancia, metodo, resultado, *args)
          unless contexto.instance_exec(resultado, &postcondicion_local) # Hacemos que ese proc se ejecute en el contexto creado, pasándole el resultado, y lanza excepción en caso de no cumplirse la condición
            raise "Excepción: El objeto #{instancia} no cumplio su postcondición"
          end
        end
      end
      
      before_and_after_each_call(nil, proc_postcondicion)
      @postcondicion = nil # Resetea la variable
    end
    
    if @before_procs.nil?  # Por otro lado, no hago nada si no se hizo before_and_after_each_call (@before_procs será nil)
      super # Solo llamo a super por si hay otra redefinición de method_added
      return
    end
    
    redefinir_metodo(method_name) # En definitiva, se redefine el metodo, para nuevos métodos añadidos
    
    super # Llamo a super por si hay otra redefinición de method_added
  end
  
  def invariant(&invariante)
    proc_invariante = proc do |instancia, method_name| # Añado el parámetro "instancia" al bloque para saber en qué contexto ejecutar el bloque, y el nombre del metodo pora cortar la recursividad
      ultimo_metodo = instancia.instance_variable_get(:@ultimo_metodo) # Leemos el nombre del último metodo que se llamó (si es el primero, será nil)
      unless ultimo_metodo == method_name # Evitamos recursividad por si el bloque llama a un getter
        instancia.instance_variable_set(:@ultimo_metodo, method_name) # Seteamos el nombre del último metodo que se llamó
        unless instancia.instance_exec(&invariante)
          raise "Excepcion: El objeto #{instancia} quedó con estado invalido. Último método ejecutado: #{method_name}" # Excepción si tras la ejecución del metodo, no se cumple la invariante
        end
      end
    end
    
    before_and_after_each_call(nil, proc_invariante)
  end
  
  # Para pre y post simplemente almacenamos las condiciones en variables
  def pre(&precondicion)
    @precondicion = precondicion
  end
  
  def post(&postcondicion)
    @postcondicion = postcondicion
  end
  
  private # Definimos como privados los metodos auxiliares para que nadie de afuera pueda usarlos
  def redefinir_metodo(method_name)
    @avoid_recursion = true # Evito recursión infinita ya que define_method llama a method_added
    
    metodo_original = instance_method(method_name) # Obtengo el metodo original (unbound method)
    define_method(method_name) do |*args, &block|
      self.class.before_procs.each do |before_proc| # Se ejecutan primero los before_procs
        before_proc.call(self, method_name, nil, *args)
      end
      retorno = metodo_original.bind(self).call(*args, &block) # En el medio se ejecuta el metodo original (primero lo bindeo a LA INSTANCIA, no al contexto)
      self.class.after_procs.each do |after_proc|
        after_proc.call(self, method_name, retorno, *args) # Luego del metodo original, se ejecutan los after_procs
      end
      retorno # Se devuelve el valor de retorno del metodo, por si se quiere utilizar
    end
    
    @avoid_recursion = false
  end
  
  def redefinir_metodos_existentes # Se redefinen los métodos que ya existían previos a before_and_after_each_call
    unless @metodos_existentes_redefinidos # Si ya se redefinieron, no se vuelven a redefinir
      
      # Filtro la lista de todos los métodos públicos, incluyendo heredados, quedándome únicamente con los definidos por la clase en particular
      metodos_publicos_definidos = instance_methods(true).filter { |metodo| method_defined?(metodo,false) }
      
      # Filtro la lista de todos los métodos privados, incluyendo heredados, quedándome únicamente con los definidos por la clase en particular
      metodos_privados_definidos = private_instance_methods(true).filter { |metodo| private_method_defined?(metodo, false) }
      
      metodos_a_modificar = metodos_publicos_definidos + metodos_privados_definidos # Unión de los métodos privados y públicos ya filtrados
      
      metodos_a_modificar.each do |method_name|
        redefinir_metodo(method_name) # Por cada metodo, lo redefino
      end
      @metodos_existentes_redefinidos = true # Flag de métodos ya redefinidos
    end
  end
  
  def construir_contexto(instancia, nombre_metodo, resultado = nil, *args)
    contexto = instancia.dup # Copiamos la instancia para mantener el contexto y añadirle singleton methods
    @parametros[nombre_metodo].each_with_index do |parametro, indice| # Por cada uno de los parámetros
      contexto.define_singleton_method(parametro) {args[indice]} # Se crea un singleton method en el objeto, el cual devuelve el valor que se pasó como argumento
    end
    
    contexto.define_singleton_method(:resultado) {resultado} # Se crea un singleton method, que retorna ese resultado
    
    contexto # Retornamos el contexto
  end

end