Having following data structure:

PaintFactory
            |
            # of colors
            customers<>
                      |
                      id
                      satisfied
                      paints<>
                             |
                             color
                             type

Preconditions
paints sorted by type, glossy first

Algorithm:
1. Customers sorted by paint number, those who have less paints at the beginning, check all customer with one color if we can satisfy them.
   if cannot return "IMPOSSIBLE", if can continue and remove customer from further processing.
2. Check the rest of customers if they can be satisfied with colors from one color customers
   if yes, remove them, if not continue.
   In parallel check if there any conflicting paints with those already in the list, if this is the case remove paint from the list.
3. After removing conflicting paint we might have customers with one color, that doesn't conflict with combine list,
   for those customers repeat 1-2 steps until no such customers left
4. Build possible path recursively for the rest of clients, as paints sorted according to the type, I assume that first found path is the best fit,
   if at least one path found return it, if not type "IMPOSSIBLE"

Test file located in test resources.



