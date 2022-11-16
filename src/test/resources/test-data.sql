INSERT INTO status VALUES
(1,'Online'),
(2,'Offline');

INSERT INTO games VALUES
(1, 'Oca'),
(2, 'Parchis');

INSERT INTO colors VALUES
(1, 'RED'),
(2, 'BLUE'),
(3, 'GREEN'),
(4, 'YELLOW');

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
(10, 'Xx_casa777rexpro_xX', 'parchis', 2,  'member'),
(11, 'mashedpotato', 'password', 1,  'member'),
(12, 'pisten', 'password', 1,  'member'),
(13, 'cortat23', 'password', 1,  'member'),
(14, 'cookiecliker1', 'password', 1,  'member');

INSERT INTO Friend(id, User1_id, User2_id, solicitingUser_id, accept, dateF) VALUES
 (1, 1, 6,1,1,'2022-03-10'),
 (2, 1, 7,1,1,'2022-03-09'),
 (3, 1, 8,1,1,'2022-03-08'),
 (4, 1, 9,1,1,'2022-03-07');
INSERT INTO Lobby(id,game_id, host_id) VALUES 
(1, 1, 4),
(2, 2, 2),
(3, 1, 6),
(4, 2, 14),
(5,1,null),
(6, 2, 13),
(7, 1, 4);

INSERT INTO lobby_players VALUES
(1,4), -- host
(1,5),
(2,2), -- host
(3,6), -- host
(3,7),
(3,8),
(3,9),
(6,13),
(4,14),
(4,11);
---(4,10);

INSERT INTO Playerstats(id, numTurnsPlayer, numDiceRolls, playerColor_id, user_id, position, 
numberOfGooses, numberOfPlayerWells,numberOfLabyrinths,numberOfPlayerPrisons,numberOfPlayerDeaths) VALUES 
(1, 20, 20, 1, 4, null, null, null, null,null,null),
(3, 25, 25, 2, 4, null, null, null, null,null,null),
(4, 35, 35, 2, 2, null, null, null, null,null,null),
(2, 30, 30, 1, 2, null, null, null, null,null,null);

INSERT INTO Match(id,game_id,numTurns,winner_id, numMatchKills,
numMatchBarriers,numMatchSpecialTiles,totalDistanceGooses,lobby_id) VALUES
(1,1,3,1,null,null,null,null,1), -- winner will have to be replaced by winner_id when association is implemented
(2,2,7,2,null,null,null,null,2),
(3,2,7,2,null,null,null,null,5);

INSERT INTO Match_Playerstats(match_id, playerstats_id) VALUES
(1,1),
(2,2),
(1,3),
(2,4);