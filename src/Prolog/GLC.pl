q1(O,X):-nominal1(Sustan),verbal1(Verbo,Y),append(Sustan,Verbo,O),X is Y.
q1(O,X):-lugar(O,Y), X is Y.
q1(O,X):-verbal1(O,Y), X is Y.
/*Pregunta 1*/
nominal1(Sustan):- saludos(S),nombre(N),append(S,N,Sustan).
nominal1(Sustan):- nombre(Sustan).
verbal1(Verbo,X):-verbos(V),lugar(L,Y),append(V,L,Verbo), X is Y.
verbal1(Verbo,X):-prepo(P),lugar(L,Y), append(P,L,Verbo), X is Y.
verbos(S):-verbo(V),prepo(P),append(V,P,S).
saludos(S):-saludo(T),waze(F),append(T,F,S).
/*pregunta 2*/
q2(O,X):-lugar(O,Y), X is Y.
q2(O,X):-verbal2(O,Y), X is Y.
verbal2(Verbo,X):-prepo(P),lugar(L,Y),append(P,L,Verbo), X is Y.
/*pregunta 3*/
q3(O,X):-verbal3(V),conec(C,Y),append(V,C,O), X is Y.
q3(O,X):-conec(O,Y),X is Y.
q3(O,X):-local(O,Y), X is Y.
verbal3(V):-verbo2(B),extra(P),append(B,P,V).
extra(P):-prepo(C),verbor(X),append(C,X,P).
extra(P):-verbor(P).
conec(C,Y):-prepo(P),local(L,Z), append(P,L,C), Y is Z.
/*pregunta 4 */
/*recibe lo que sea */
/*pregunta 5 */
q5(O,X):-verbal5(V),extra5(C,Y),append(V,C,O), X is Y.
q5(O,X):-extra5(O,Y),X is Y.
verbal5(B):-se(S),verboL(V),append(S,V,B).
extra5(X,Y):-prepo(P),lugar(L,C),append(P,L,X), Y is C.
se(["se"]).
verboL(["localiza"]).
verboL(["ubica"]).
verboL(["encuentra"]).
verboL(["establece"]).
verbor(["pasar"]).
verbor(["viajar"]).
verbor(["ir"]).
verbor(["comprar"]).
verbor(["desviar"]).
verbor(["vender"]).
verbor(["comer"]).
saludo(["hola"]).
saludo(["buenas"]).
verbo(["estoy"]).
verbo(["ando"]).
verbo(["voy"]).
verbo(["habito"]).
verbo2(["tengo"]).
verbo2(["deseo"]).
verbo2(["quiero"]).
verbo2(["tendria"]).
nombre(["yo"]).
nombre(["me"]).
waze(["wazelog"]).
prepo(["en"]).
prepo(["por"]).
prepo(["para"]).
prepo(["que"]).
prepo(["al"]).
lugar(["guanacaste"],7).
lugar(["san jose"],6).
lugar(["limon"],5).
lugar(["puntarenas"],4).
lugar(["alajuela"],3).
lugar(["heredia"],2).
lugar(["cartago"],1).
local(["super"],1).
local(["restaurante"],2).
local(["supermercado"],3).
local(["colegio"],4).
local(["medico"],5).
local(["hospital"],6).
local(["instituto"],7).
local(["tec"],8).
local(["salon"],9).
local(["parque"],10).
local(["psicologo"],11).
local(["hogar"],12).
local(["mecanico"],13).
local(["culto"],14).
local(["hotel"],15).










