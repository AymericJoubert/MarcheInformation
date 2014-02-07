--
-- Script de création des tables pour la base de données du projet Lille Information Market
--


--
-- Table des acheteurs/vendeurs
--
DROP TABLE IF EXISTS offre;
DROP TABLE IF EXISTS marche;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users_confirm;
DROP TABLE IF EXISTS user_roles;

CREATE TABLE users(
  user_id serial,
  user_name varchar(255) UNIQUE,
  user_pass varchar(255),
  nom varchar(255),
  prenom varchar(255),
  email varchar(255),
  cash int,
  CONSTRAINT pk_user_id PRIMARY KEY (user_id)
);

CREATE TABLE user_roles(
  user_name varchar(255),
  role_name varchar(255)
);

--
-- Table des inscriptions
--

CREATE TABLE users_confirm(
  user_name varchar(255) UNIQUE,
  user_pass varchar(255),
  nom varchar(255),
  prenom varchar(255),
  email varchar(255),
  confirm_code varchar(255),
  CONSTRAINT pk_user_confirm_id PRIMARY KEY (user_name)
);



--
-- Table des marché de l'information
--


CREATE TABLE marche(
  marche_id serial,
  createur int NOT NULL,
  question varchar(255) NOT NULL,
  ouverture timestamp NOT NULL,
  fermeture timestamp NOT NULL,
  inverse int,
  reussite boolean,
  CONSTRAINT pk_marche_id PRIMARY KEY (marche_id),
  CONSTRAINT fk_createur FOREIGN KEY (createur) REFERENCES users (user_id),
  CONSTRAINT fk_inverse FOREIGN KEY (inverse) REFERENCES marche (marche_id)
);

--
-- Table des offres mise en jeu par les traders
--

CREATE TABLE offre(
  offre_id serial,
  valeur int,
  marche int,
  acheteur int,
  acheteur_inverse int,
  offre_date timestamp,
  achat boolean NOT NULL DEFAULT true,
  CONSTRAINT pk_offre_id PRIMARY KEY (offre_id),
  CONSTRAINT fk_marche FOREIGN KEY (marche) REFERENCES marche (marche_id),
  CONSTRAINT fk_acheteur FOREIGN KEY (acheteur) REFERENCES users (user_id),
  CONSTRAINT fk_acheteur_inverse FOREIGN KEY (acheteur_inverse) REFERENCES users (user_id)
);

--
-- Quelques lignes pour tester
--

insert into users (user_name, user_pass, nom,prenom,cash) values ('blondeah', 'fifou', 'pirex','paul',10000);
insert into users (user_name, user_pass, nom,prenom,cash) values ('aymeric', 'fifou', 'bouicx','pierre',10000);
insert into user_roles values ('aymeric','role1');
insert into user_roles values ('blondeah','role1');

--insert into marche (createur,question,ouverture,fermeture) values (,,CURRENT_DATE,);

insert into marche (createur,question,ouverture,fermeture) values (1,'M picault va donner les notes en janvier?',CURRENT_DATE,'2014-02-01');
insert into marche (createur,question,ouverture,fermeture,inverse) values (1,'M picault va donner les notes après janvier?',CURRENT_DATE,'2014-02-01',1);
UPDATE marche SET inverse = 2 WHERE marche_id = 1;


insert into marche (createur,question,ouverture,fermeture) values (2,'Demode va arriver à l heure une fois en janvier?',CURRENT_DATE,'2014-02-01');
insert into marche (createur,question,ouverture,fermeture,inverse) values (2,'Demode n arrivera jamais à l heure en janvier',CURRENT_DATE,'2014-02-01',3);
UPDATE marche SET inverse = 4 WHERE marche_id = 3;

--
-- Test des offres
--

--les offre d'un marché

--insert into offre (valeur,marche,acheteur) values (40,1, 1);
--insert into offre (valeur,marche,acheteur) values (38, 2,2);
insert into offre (valeur,marche,acheteur) values (60, 3, 2);
--insert into offre (valeur,marche,acheteur) values (70, 4, 1);
insert into offre (valeur,marche,acheteur,achat) values (40, 4, 2, false);
--insert into offre (valeur,marche,acheteur,achat) values (30, 3, 1, false);

-- les offre le l'autre marché

--insert into offre (valeur,marche,acheteur) values (38,4,1);
--insert into offre (valeur,marche,acheteur) values (47,3,2);
--insert into offre(valeur,marche,acheteur,offre_date) values (40,1,2,'2014-01-30 15:38:24');

update offre SET offre_date = '2014-01-05 11:30:45' where offre_id=1;
update offre SET offre_date = '2013-12-22 10:30:45' where offre_id=4;
update offre SET offre_date = '2013-12-18 11:30:45' where offre_id=3;
update offre SET offre_date = '2013-12-14 11:30:45' where offre_id=2;