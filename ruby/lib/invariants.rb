require_relative 'before_and_after'

module Invariante
    def invariant(&invariante)
        proc_invariante = proc do
            unless instance_exec(&invariante)
                raise "No cumple con condicion"
            end
        end
        before_and_after_each_call(proc {}, proc_invariante)
    end
end

=begin
module Invariante
    def invariant(&block)
        @invariant_block = block

        wrapper = proc do |instance|
            instance.instance_eval do
                unless instance_eval(&block)
                    raise "Excepción: estado inválido"
                end
            end
        end
        before_and_after_each_call(proc{}, wrapper)
    end
end
=end
# TODO: Chequear que funcione
