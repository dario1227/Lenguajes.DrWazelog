:-include('Lugares.pl').
:-include('Arcos.pl').
q1(O,X):-nominal1(Sustan),verbal1(Verbo,X),append(Sustan,Verbo,O).
q1(X,X):-lugar(X).
q1(O,X):-verbal1(O,X).
/*Pregunta 1*/
nominal1(Sustan):- saludos(S),nombre(N),append(S,N,Sustan).
nominal1(Sustan):- nombre(Sustan).
verbal1(Verbo,X):-verbos(V),lugar(X),append(V,X,Verbo).
verbal1(Verbo,X):-prepo(P),lugar(X), append(P,X,Verbo).
verbos(S):-verbo(V),prepo(P),append(V,P,S).
saludos(S):-saludo(T),waze(F),append(T,F,S).
/*pregunta 2*/
q2(X,X):-lugar(X).
q2(O,X):-verbal2(O,X).
verbal2(Verbo,X):-prepo(P),lugar(X),append(P,X,Verbo).
/*pregunta 3*/
q3(O,X):-verbal3(V),conec(C,X),append(V,C,O).
q3(O,X):-conec(O,X).
q3(X,X):-local(X).
q3(X,X):-no(X).
verbal3(V):-verbo2(B),extra(P),append(B,P,V).
extra(P):-que(C),verbor(X),append(C,X,P).
extra(P):-verbor(P).
conec(C,Y):-prepo2(P),local(Y), append(P,Y,C).
/*pregunta 4 */
/*recibe lo que sea */
/*pregunta 5 */
q5(O,X):-verbal5(V),extra5(C,X),append(V,C,O).
q5(O,X):-extra5(O,X).
verbal5(B):-se(S),verboL(V),append(S,V,B).
extra5(X,Y):-prepo(P),lugar(Y),append(P,Y,X).
se(['se']).
verboL(['localiza']).
verboL(['ubica']).
verboL(['encuentra']).
verboL(['establece']).
verbor(['pasar']).
verbor(['viajar']).
verbor(['ir']).
verbor(['comprar']).
verbor(['desviar']).
verbor(['vender']).
verbor(['comer']).
saludo(['hola']).
saludo(['buenas']).
verbo(['estoy']).
verbo(['ando']).
verbo(['voy']).
verbo(['habito']).
verbo2(['tengo']).
verbo2(['deseo']).
verbo2(['quiero']).
verbo2(['tendria']).
nombre(['yo']).
nombre(['me']).
waze(['wazelog']).
prepo(['en']).
prepo(['por']).
prepo(['para']).
que(['que']).
prepo2(['al']).
no(['no']).
local(['super']).
local(['restaurante']).
local(['supermercado']).
local(['colegio']).
local(['medico']).
local(['hospital']).
local(['instituto']).
local(['tec']).
local(['salon']).
local(['parque']).
local(['psicologo']).
local(['hogar']).
local(['mecanico']).
local(['culto']).
local(['hotel']).
/************************************************GRAFO***********************************************************/
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












