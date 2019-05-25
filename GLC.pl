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
lugar(['guanacaste']).
lugar(['san jose']).
lugar(['limon']).
lugar(['puntarenas']).
lugar(['alajuela']).
lugar(['heredia']).
lugar(['cartago']).
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










