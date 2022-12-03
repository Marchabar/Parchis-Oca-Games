INSERT INTO status VALUES
(1,'Online'),
(2,'Offline'),
(3,'Away');

INSERT INTO games VALUES
(1, 'Oca'),
(2, 'Parchis');

INSERT INTO colors (id, name, rgb) VALUES
(1, 'RED','#ff3d3d'),
(2, 'BLUE','#3857f2'),
(3, 'GREEN','#84f54c'),
(4, 'YELLOW','#e6e62c');

INSERT INTO User(id, login, password, status_id, role, prefcolor_id) VALUES
(1, 'pepito', 'pepazo', 1, 'admin',2),
(2, 'Roll20_2DS', 'password', 1, 'member',4),
(3, 'DeOcaEnOca', 'parchis', 2,  'member',1),
(4, 'luke1', 'pepazo', 1,  'member',1),
(5, 'susato', 'mikotoba', 1,  'member',2),
(6, 'josemicrack', 'pepazo', 1,  'member',2),
(7, 'josemiidolo', 'password', 3,  'member',3),
(8, 'josemimastodonte', 'pepazo', 1,  'member',2),
(9, 'josemifiera', 'password', 1,  'member',3),
(10, 'Xx_casa777rexpro_xX', 'parchis', 2,  'member',3),
(11, 'mashedpotato', 'password', 1,  'member',3),
(12, 'pisten', 'password', 1,  'member',3),
(13, 'cortat23', 'password', 1,  'member',3),
(14, 'cookiecliker1', 'password', 3,  'member',4),
(15, '123', '123',1,'member',4);

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
(6, 2, 13);

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

INSERT INTO Playerstats(id, numTurnsPlayer, numDiceRolls, playerColor_id, user_id, position,turnsStuck, 
numberOfGooses, numberOfPlayerWells,numberOfLabyrinths,numberOfPlayerPrisons,numberOfPlayerDeaths,numberOfInns) VALUES 
(1, 0, 0, 1 ,1, 0, 0, 2, 1, 1, 0, 0,0),
(2, 30, 30, 1, 1, 23,0, 1, 0, 2, 1, 0,0),
(3, 0, 0, 2, 2, 60,0, 3, 2, 1, 1, 0,0),
(4, 35, 35, 2, 2, 13,0, 4, 0, 0, 1, 1,0),
(5, 200, 32, 3 ,1, 42,0, 2, 0, 2, 0, 0,0),
(6, 54, 15, 3, 1, 31,0, 0, 2, 0, 0, 0,0),
(7, 72, 84, 3, 1, 2,0, 5, 1, 1, 0, 1,0),
(8, 15, 54, 3, 1, 23,0, 8, 0, 1, 2, 1,0);

INSERT INTO Match(id,game_id,numTurns,winner_id, lastRoll, numMatchKills,
numMatchBarriers,numMatchSpecialTiles,totalDistanceGooses,lobby_id,playertoplay_id) VALUES
(1,1,0,1,0,null,null,null,null,1,1), -- winner will have to be replaced by winner_id when association is implemented
(2,2,7,2,0,null,null,null,null,1,2);

INSERT INTO Match_Playerstats(match_id, playerstats_id) VALUES
(1,1),
(2,2),
(1,3),
(2,4);

INSERT INTO tiletypes VALUES
(1, 'NORMAL'),
(2, 'OCA'),
(3, 'BRIDGE'),
(4, 'INN'),
(5, 'WELL'),
(6, 'DICE'),
(7, 'LABYRINTH'),
(8, 'PRISON'),
(9, 'DEATH'),
(10, 'END');

INSERT INTO OcaTile(id, tiletype_id) VALUES
(1,2),
(2,1),
(3,1),
(4,1),
(5,2),
(6,3),
(7,1),
(8,1),
(9,2),
(10,1),
(11,1),
(12,3),
(13,1),
(14,2),
(15,1),
(16,1),
(17,1),
(18,2),
(19,4),
(20,1),
(21,1),
(22,1),
(23,2),
(24,1),
(25,1),
(26,6),
(27,2),
(28,1),
(29,1),
(30,1),
(31,5),
(32,2),
(33,1),
(34,1),
(35,1),
(36,2),
(37,1),
(38,1),
(39,1),
(40,1),
(41,2),
(42,7),
(43,1),
(44,1),
(45,2),
(46,1),
(47,1),
(48,1),
(49,1),
(50,2),
(51,1),
(52,8),
(53,6),
(54,2),
(55,1),
(56,1),
(57,1),
(58,9),
(59,2),
(60,1),
(61,1),
(62,1),
(63,10);


INSERT INTO MessageChat(id, description, time, match_id, user_id) VALUES
(1,'GG guys', '15:55:10', 1, 1);
