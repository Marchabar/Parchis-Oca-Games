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

INSERT INTO status VALUES
(1,'Online'),
(2,'Offline');

INSERT INTO games VALUES
(1, 'Oca'),
(2, 'Parchis');

INSERT INTO User(id, login, password, status_id, role) VALUES
(1, 'pepito', 'pepazo', 1, 'admin'),
(2, 'Roll20_2DS', 'password', 1, 'member'),
(3, 'DeOcaEnOca', 'parchis', 2,  'member'),
(4, 'luke1', 'pepazo', 1,  'member'),
(5, 'susato', 'mikotoba', 1,  'member'),
(6, 'josemicrack', 'pepazo', 1,  'member'),
(7, 'josemiidolo', 'password', 1,  'member'),
(8, 'josemimastodonte', 'pepazo', 1,  'member'),
(9, 'josemifiera', 'password', 1,  'member'),
(10, 'Xx_casa777rexpro_xX', 'parchis', 2,  'member');

INSERT INTO Lobby(id,game_id, host_id) VALUES 
(1, 1, 4),
(2, 2, 2),
(3, 1, 6);

INSERT INTO lobby_players VALUES
(1,4), -- host
(1,5),
(2,2), -- host
(3,6), -- host
(3,7),
(3,8),
(3,9);

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


INSERT INTO Match(id,game_id,numTurns,winner, numMatchKills,numMatchBarriers,numMatchSpecialTiles,totalDistanceGooses,lobby_id) VALUES
(1,1,3,'pepe',null,null,null,null,1), -- winner will have to be replaced by winner_id when association is implemented
(2,2,7,'maria',null,null,null,null,2);