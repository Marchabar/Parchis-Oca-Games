INSERT INTO Board(id, shortname,description) VALUES
(2, 'MESA', 'Mesa del Parlamento'),
(3, 'JP', 'Junta de Portavoces'),
(4, 'PPA', 'Pleno del Parlamento'),
(5, 'DIPPER', STRINGDECODE('Diputaci\u00f3n Permamente')),
(6, 'GPS', 'G.P. Socialista'),
(7, 'IULV-CA', STRINGDECODE('G.P. Izquierda Unida Los Verdes-Convocatoria por Andaluc\u00eda')),
(8, 'GPP', 'G.P. Popular Andaluz'),
(9, 'GPPD', STRINGDECODE('G.P. Podemos Andaluc\u00eda')),
(10, 'GPC', 'G.P. Ciudadanos'),
(11, 'GOBIERNO', 'Gobierno');

INSERT INTO Room(id,description,active) VALUES 
(1,'Sala de Plenos',1),
(2,'Sala de Junta de Portavoces',1);

INSERT INTO User(id,login,password,role) VALUES
(1,'member1','m3mb3r','member'),
(2,'admin1','4dm1n','admin');

INSERT INTO MEMBER (id, name) VALUES 
(1,'Chikito de la Calzada'),
(2,'Gila'),
(3,'Tip'),
(4,'Coll'),
(5,'Eugenio');

INSERT INTO Member_boards (members_id,boards_id) VALUES
(1,11), -- Chikito for president
(2,11), -- Gila for minister of defence
(3,11), -- Tip for  minister of economy
(4,11), -- Coll for minister of education
(5,11); -- Eugenio for minister of health
