edge("Cartago","San Jose",1).
edge("Cartago","Limon",3.5).
edge("Cartago","Heredia",2.5).
edge("San Jose","Heredia",1).
edge("San Jose","Alajuela",2.5).
edge("Heredia","Limon",1).
edge("Heredia","Alajuela",2.2).
edge("Limon","Alajuela",1).
lugares("Limon").
lugares("Heredia").

connected(X,Y,L) :- edge(X,Y,L) ; edge(Y,X,L).

path(A,B,Path,Len) :-
       travel(A,B,[A],Q,Len),
       reverse(Q,Path).

travel(A,B,P,[B|P],L) :-
       connected(A,B,L).
travel(A,B,Visited,Path,L) :-
       connected(A,C,D),
       C \== B,
       \+member(C,Visited),
       travel(C,B,[C|Visited],Path,L1),
       L is D+L1.

shortest(A,B,Path,Length) :-
   setof([P,L],path(A,B,P,L),Set),
   Set = [_|_], % fail if empty
   minimal(Set,[Path,Length]).

minimal([F|R],M) :- min(R,F,M).

% minimal path
min([],M,M).
min([[P,L]|R],[_,M],Min) :- L < M, !, min(R,[P,L],Min).
min([_|R],M,Min) :- min(R,M,Min).

append_1([],X,X).
append_1([X|W],Z,[X|C]):- append(W,Z,C).

get_whole_path([],_,[],0).
get_whole_path([X |Destinos],Origen,Camino,Largo):-
       shortest(Origen,X,Camino_2,Largo_2),
      append_1(Camino_2,Camino_fin,Camino),
       get_whole_path(Destinos,X,Camino_fin,Largo_final),Largo is Largo_final + Largo_2.

delete_one(_, [], []).
delete_one(Term, [Term|Tail], Tail).
delete_one(Term, [Head|Tail], [Head|Result]) :-
  delete_one(Term, Tail, Result),!.


delete_all([],X,X).
delete_all([X|Z],Lista,Resultado):-
       delete_one(X,Lista,Result),delete_all(Z,Result,Resultado).


