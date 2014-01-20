--
-- Script de création des tables pour la base de données du projet Lille Information Market
--


--
-- Table des acheteurs/vendeurs
--
DROP TABLE IF EXISTS offre;
DROP TABLE IF EXISTS marche;
DROP TABLE IF EXISTS trader;

CREATE TABLE trader(
  trader_id serial,
  nom varchar(255),
  prenom varchar(255),
  cash int,
  CONSTRAINT pk_trader_id PRIMARY KEY (trader_id)
);

--
-- Table des marché de l'information
--


CREATE TABLE marche(
  marche_id serial,
  createur int,
  question varchar(255),
  ouverture date,
  fermeture date,
  inverse int,
  CONSTRAINT pk_marche_id PRIMARY KEY (marche_id),
  CONSTRAINT fk_createur FOREIGN KEY (createur) REFERENCES trader (trader_id),
  CONSTRAINT fk_inverse FOREIGN KEY (inverse) REFERENCES marche (marche_id)
);

--
-- Table des offres mise en jeu par les traders
--

CREATE TABLE offre(
  offre_id serial,
  valeur int,
  quantite int,
  marche int,
  acheteur int,
  acheteur_inverse int,
  CONSTRAINT pk_offre_id PRIMARY KEY (offre_id),
  CONSTRAINT fk_marche FOREIGN KEY (marche) REFERENCES marche (marche_id),
  CONSTRAINT fk_acheteur FOREIGN KEY (acheteur) REFERENCES trader (trader_id),
  CONSTRAINT fk_acheteur_inverse FOREIGN KEY (acheteur_inverse) REFERENCES trader (trader_id)
);

--
-- Quelques lignes pour tester
--

insert into trader (nom,prenom,cash) values ('pirex','paul',10000);
insert into trader (nom,prenom,cash) values ('bouicx','pierre',10000);

--insert into marche (createur,question,ouverture,fermeture) values (,,CURRENT_DATE,);

insert into marche (createur,question,ouverture,fermeture) values (1,'M picault va donner les notes en janvier?',CURRENT_DATE,'2014-02-01');
insert into marche (createur,question,ouverture,fermeture,inverse) values (1,'M picault va donner les notes après janvier?',CURRENT_DATE,'2014-02-01',1);
UPDATE marche SET inverse = 2 WHERE marche_id = 1;


insert into marche (createur,question,ouverture,fermeture) values (2,'Demode va arriver à l heure une fois en janvier?',CURRENT_DATE,'2014-02-01');
insert into marche (createur,question,ouverture,fermeture,inverse) values (2,'Demode n arrivera jamais à l heure en janvier',CURRENT_DATE,'2014-02-01',1);
UPDATE marche SET inverse = 4 WHERE marche_id = 3;

--
-- Test des offres
--

--les offre d'un marché

insert into offre (valeur,quantite,marche,acheteur) values (40, 10, 1, 1);
insert into offre (valeur,quantite,marche,acheteur) values (38, 7, 2,2);

-- les offre le l'autre marché

insert into offre (valeur,quantite,marche,acheteur) values (38, 12, 4,1);
insert into offre (valeur,quantite,marche,acheteur) values (47, 2, 3,2);