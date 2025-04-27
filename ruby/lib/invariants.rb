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
