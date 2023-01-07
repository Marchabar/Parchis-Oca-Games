INSERT INTO status VALUES
(1,'Online'),
(2,'Offline');

INSERT INTO games VALUES
(1, 'Oca'),
(2, 'Parchis');

INSERT INTO colors (id, name, rgb) VALUES
(1, 'RED','#f43e3e'),
(2, 'BLUE','#3d5cf9'),
(3, 'GREEN','#4c9c24'),
(4, 'YELLOW','#bf870f');

INSERT INTO User(id, login, password, status_id, role, prefcolor_id) VALUES
(1, 'pepito', '$2y$10$AnTs0JSo4ifIyJxhi/uEsOLoN4jkLvDF/R4/8PSYjHoGuecFOHKLi', 2, 'admin',2),
(2, 'Roll20_2DS', '$2y$10$Ky3jcJX1oGeDZhXfp4.Dcu.kn9gejHm.QTH0/gB5Z3/eHkT5JehfK', 2, 'member',4),
(3, 'DeOcaEnOca', '$2y$10$VmzfLfnRdOYQKdwz2uie8uDLrzsVbBl9pQ7gE9MsqCExMkb4.OrOO', 2,  'member',1),
(4, 'luke1', '$2y$10$nKau3KxL4hylcl9XYqP5Pun8Pq8x5Bz8L0ePfkH0Qf2jeHJQmAkje', 2,  'member',2),
(5, 'susato', '$2y$10$u98NB2OUIQUoe3Hlt2svhear7WMYN70KigA.0OJecWIAtWoatws.6', 2,  'member',3),
(6, 'josemicrack', '$2y$10$h45oF7U1PPhVDVNaD79ZguinfN6rOVGdoo1e0nMiG5OIpod8WdJvC', 2,  'member',4),
(7, 'josemiidolo', '$2y$10$3RcCPhxqZUJS/Bk6yxVGp.nyjzp9iCB0HldlP15tcSso.tUdqPe.6', 2,  'member',1),
(8, 'josemimastodonte', '$2y$10$dkG/UMaXrGAf/eQGGgJO7u1MsboJqdPjWt2DooC0mq1kZHuP7t.zC', 2,  'member',2),
(9, 'josemifiera', '$2y$10$4J5mbXKeegM0O0qv/1jYLOjDE9j6WJzdQY07bZdA.YGjwtTV/G9La', 2,  'member',3),
(10, 'Xx_casa777rexpro_xX', '$2y$10$zk78IuNE.6tAwlHyAbqtiuCrB5Ms032edEA2ZqERey03GeHlu6Wry', 2,  'member',3),
(11, 'mashedpotato', '$2y$10$mpFhBNbgXjPX3rsbLPdr1emTDtFfpPRUOFbdkYj3YsRyrex.UBefO', 2,  'member',1),
(12, 'pisten', '$2y$10$quMOZjf.ecqO5D5oozju2u2MBAPyMDfxYfVwbspah7IWha8/1m9d2', 2,  'member',3),
(13, 'cortat23', '$2y$10$zHcudOaCmzrFAVcGYfaRhOPfVgkL8RAbPDsQIQ2B7zNpv1avRh9NK', 2,  'member',3),
(14, 'cookiecliker1', '$2y$10$NbKlxSDCcegyrpIIHZTw2ufYcrYHG5qGnQj5RQ9WbzLWGm5NjN2da', 2,  'member',4),
(15, '123', '$2y$10$Qep/M7DLIqotunK9Kkl8auXVtoHMSbrFZuFmE63Q4ApVM.vjQ/2Fy',2,'member',4),
(16, 'offlineguy', '$2y$10$OM4u6hvgshxZQWC84py7Nexx044jhft5HKkQsdWJOAjQQpzGEM/TG', 2, 'member',4),
(17, 'awayguy', '$2y$10$P8uh/JypcRC64cwVjssh1Ow.M1otsgSeUhaybTnZvHWk.mIeEnyEa', 2, 'member',1),
(18, 'onlineguy', '$2y$10$P8uh/JypcRC64cwVjssh1Ow.M1otsgSeUhaybTnZvHWk.mIeEnyEa', 2, 'member',4),
(19, 'parchisboy', '$2y$10$P8uh/JypcRC64cwVjssh1Ow.M1otsgSeUhaybTnZvHWk.mIeEnyEa', 2, 'member',2);

INSERT INTO Friend(id, User1_id, User2_id, solicitingUser_id, accept, dateF) VALUES
(1, 1, 6,1,1,'2022-03-10'),
(2, 1, 7,1,1,'2022-03-09'),
(3, 1, 8,1,1,'2022-03-08'),
(4, 1, 9,1,1,'2022-03-07'),
(5, 1, 3,1,1,'2022-03-07'),
(6, 1, 4,1,1,'2022-03-07'),
(7, 1, 16,1,1,'2022-03-06'),
(8, 1, 17,1,1,'2022-03-06'),
(9, 1, 19,1,1,'2022-03-06'),
(10, 1, 18,1,1,'2022-03-06'),
(11, 1, 5,1,1,'2023-01-04'),
(12, 1, 10,1,1,'2022-03-06'),
(13, 1, 11,1,1,'2023-01-04');



INSERT INTO Lobby(id,game_id, host_id) VALUES 
(1, 1, 6),
(2, 2, 11),
(3, 2, 3),
(4, 2, 18),
(5,1,null),
(6, 1, 13);

INSERT INTO lobby_players VALUES
(1,6), --host
(1,5), 
(1,7), 
(1,8), 
(2,11), --host
(2,10),
(3,3), --host
(3,1),
(3,4),
(4,18), --host
(4,9),
(4,17),
(4,19),
(6,13), --host
(6,14);
--Oca playerstats
INSERT INTO Playerstats(id, numDiceRolls, playerColor_id, user_id, position,turnsStuck, 
numberOfGooses, numberOfPlayerWells,numberOfLabyrinths,numberOfPlayerPrisons,numberOfPlayerDeaths,numberOfInns) VALUES 
(1, 34, 1, 1, 63, 0,2,1,0,2,1,5),
(2, 37, 2, 2, 54, 0,1,6,3,2,2,2),

(7, 33, 1, 1, 63, 0,2,2,6,1,0,2),
(8, 21, 2, 2, 13, 0,2,3,5,0,0,5),
(9, 31, 3, 3, 32 , 2,2,0,1,0,0,5),
(10, 20, 4, 4, 23, 0,0,0,2,3,0,5),

(17, 52, 3, 13, 63, 0,20,0,0,0,1,0),
(18, 35, 4, 14, 20, 0,6,1,0,0,3,0),

(19, 52, 3, 13, 63, 0,20,0,0,6,0,2),
(20, 35, 4, 14, 20, 0,6,1,0,0,3,0),

(21, 52, 3, 13, 63, 0,20,0,0,0,0,1),
(22, 35, 4, 14, 20, 0,6,1,0,0,3,0),

(23, 34, 3, 5, 52, 4,20,0,0,0,0,0),
(24, 45, 4, 6, 20, 0,6,1,0,0,3,0),
(25, 52, 1, 7, 62, 0,20,0,0,0,0,0),
(26, 45, 2, 8, 20, 0,6,1,0,0,3,0);

--Parchis playerstats
INSERT INTO Playerstats(id, numDiceRolls, playerColor_id, user_id, numberOfCheats, numberOfChipsOut,
numberOfBarriersFormed, numberOfEndchips, numberOfBarrierRebound, numberOfChipsEaten) VALUES
(3, 45, 2, 10, 0, 4, 0,4,6,0),
(4, 42, 4, 11, 0, 3, 1,0,0,0),

(5, 46, 2, 1, 0, 5, 1,4,0,0),
(6, 42, 4, 3, 0, 2, 0,1,3,1),

(11,80, 3, 10,4, 8, 4,6,1,8),
(12,96, 1, 11,0,12,2,1,5,7),

(27,	35,3,	9,	0,	1,	0,	2,	4,	0),
(28,	33,1,	17,	2,	3,	0,	2,	4,	1),
(29,	30,4,	18,	1,	1,	0,	2,	6,	0),
(30,	31,2,	19,	2,	0,	0,	0,	6,	0);

INSERT INTO achievementTypes (id, name) VALUES
(1, 'DICE'),
(2, 'FRIENDS'),
(3, 'GOOSE'),
(4, 'MATCHES_PLAYED'),
(5, 'WINS'),
(6, 'ADVANCE'),
(7, 'WELL'),
(8, 'MAZE'),
(9, 'PRISON'),
(10, 'DEATH');

INSERT INTO Achievement(id, name, description, fileImage, type_id, value) VALUES
(1, 'Roller 1', 'Roll the dice up to 1 point or more', 'dice', 1,1),
(2, 'Roller 10', 'Roll the dice up to 10 points or more', 'dice', 1,10),
(3, 'Roller 100', 'Roll the dice up to 100 points or more', 'dice', 1,100),
(4, 'Roller 1000', 'Roll the dice up to 1000 points or more', 'dice', 1,1000),
(5, 'Friend 1', 'Have 1 or more friends', 'friend', 2, 1),
(6, 'Friend 5', 'Have 5 or more friends', 'friend', 2, 5),
(7, 'Friend 10', 'Have 10 or more friends', 'friend', 2, 10),
(8, 'Friend 20', 'Have 20 or more friends', 'friend', 2, 20),
(9, 'Goose 1', 'Fall 1 or more times in a Goose tile', 'goose', 3, 1),
(10, 'Goose 50', 'Fall 50 or more times in a Goose tile', 'goose', 3, 50),
(11, 'Goose 100', 'Fall 100 or more times in a Goose tile', 'goose', 3, 100),
(12, 'Goose 200', 'Fall 200 or more times in a Goose tile', 'goose', 3, 200),
(13, 'Player 1', 'Play 1 or more matches', 'player', 4, 1),
(14, 'Player 10', 'Play 10 or more matches', 'player', 4, 10),
(15, 'Player 100', 'Play 100 or more matches', 'player', 4, 100),
(16, 'Player 1000', 'Play 1000 or more matches', 'player', 4, 1000),
(17, 'Winner 1', 'Win 1 or more matches', 'crown', 5, 1),
(18, 'Winner 5', 'Win 5 or more matches', 'crown', 5, 5),
(19, 'Winner 10', 'Win 10 or more matches', 'crown', 5, 10),
(20, 'Winner 20', 'Win 20 or more matches', 'crown', 5, 20),
(21, 'Advance 1', 'Advance 1 or more tiles', 'advance', 6, 1),
(22, 'Advance 10', 'Advance 10 or more tiles', 'advance', 6, 10),
(23, 'Advance 100', 'Advance 100 or more tiles', 'advance', 6, 100),
(24, 'Advance 1000', 'Advance 1000 or more tiles', 'advance', 6, 1000),
(25, 'Well 1', 'Fall 1 or more times in the well','well', 7, 1),
(26, 'Well 5', 'Fall 5 or more times in the well','well', 7, 5),
(27, 'Well 10', 'Fall 10 or more times in the well','well', 7, 10),
(28, 'Well 20', 'Fall 20 or more times in the well','well', 7, 20),
(29, 'Maze 1', 'Get lost 1 or more times in the maze', 'maze', 8, 1),
(30, 'Maze 5', 'Get lost 5 or more times in the maze', 'maze', 8, 5),
(31, 'Maze 10', 'Get lost 10 or more times in the maze', 'maze', 8, 10),
(32, 'Maze 20', 'Get lost 20 or more times in the maze', 'maze', 8, 20),
(33, 'Prison 1', 'Go to prison 1 or more times', 'prison', 9, 1),
(34, 'Prison 5', 'Go to prison 5 or more times', 'prison', 9, 5),
(35, 'Prison 10', 'Go to prison 10 or more times', 'prison', 9, 10),
(36, 'Prison 20', 'Go to prison 20 or more times', 'prison', 9, 20),
(37, 'Death 1', 'Die 1 or more times', 'death', 10, 1),
(38, 'Death 5', 'Die 5 or more times', 'death', 10, 5),
(39, 'Death 10', 'Die 10 or more times', 'death', 10, 10),
(40, 'Death 20', 'Die 20 or more times', 'death', 10, 20);



INSERT INTO Match(id,game_id,numTurns,winner_id, lastRoll,lobby_id,playertoplay_id, event, cheaterCounter) VALUES
--Oca
(1,1,21,1,3,1,1,null,0),
(4,1,34,7,6,1,7,null,0),
(7,1,5,17,2,5,17,null,0),
(8,1,5,19,2,5,19,null,0),
(9,1,5,21,2,5,21,null,0),
(10,1,23,null,2,1,24,null,0),

--Parchis
(2,2,43,3,4,2,4,null,0),
(3,2,66,6,2,1,6,null,0),
(5,2,26,null,5,2,11,null,0),
(6,2,25,null,4,4,29,null,0);

INSERT INTO Match_Playerstats(match_id, playerstats_id) VALUES
(1,1),
(1,2),

(4,7),
(4,8),
(4,9),
(4,10),

(7,17),
(7,18),

(8,19),
(8,20),

(9,21),
(9,22),

(10,23),
(10,24),
(10,25),
(10,26),

(2,3),
(2,4),

(3,5),
(3,6),

(5,11),
(5,12),

(6,	27),
(6,	28),
(6,	29),
(6,	30);

INSERT INTO chip(id, absolutePosition, relativePosition, prefColor_id) VALUES

(1,7,63,2),
(2,22,10,2),
(3,65,100,2),
(4,0,0,2),

(5,71,100,4),
(6,71,100,4),
(7,71,100,4),
(8,71,100,4),

(9,7,63,2),
(10,22,10,2),
(11,65,100,2),
(12,0,0,2),

(13,71,100,4),
(14,71,100,4),
(15,71,100,4),
(16,71,100,4),

(17,7,46,3),
(18,71,100,3),
(19,65,100,3),
(20,0,0,3),

(21,65,100,1),
(22,71,100,1),
(23,71,100,1),
(24,71,100,1),

(25,68,100,3),
(26,46,17,3),
(27,0,39,3),
(28,4,43,3),

(29,71,100,1),
(30,12,17,1),
(31,32,37,1),
(32,0,0,1),

(33,68,100,4),
(34,0,56,4),
(35,13,1,4),
(36,2,58,4),

(37,56,10,2),
(38,0,22,2),
(39,5,27,2),
(40,0,0,2);

INSERT INTO playerstats_chips VALUES
(3,1),
(3,2),
(3,3),
(3,4),

(4,5),
(4,6),
(4,7),
(4,8),

(5,9),
(5,10),
(5,11),
(5,12),

(6,13),
(6,14),
(6,15),
(6,16),

(11,17),
(11,18),
(11,19),
(11,20),

(12,21),
(12,22),
(12,23),
(12,24),

(27,	25),
(27,	26),
(27,	27),
(27,	28),

(28,	29),
(28,	30),
(28,	31),
(28,	32),

(29,	33),
(29,	34),
(29,	35),
(29,	36),

(30,	37),
(30,	38),
(30,	39),
(30,	40);

INSERT INTO messageChat VALUES
(1, 'I believe I have found myself in an inconvenient situation', '16:23:32', 6, 9),
(2, 'True indeed', '16:23:34', 6, 17),
(3, 'Lmao', '16:23:35', 6, 19);

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

INSERT INTO parchistiletypes VALUES
(1, 'NORMALRED'), --the normal tiles where everyone can go. the fifth tile is their start. 
(2, 'NORMALBLUE'), --separated by color to differentiate sections
(3, 'NORMALGREEN'),
(4, 'NORMALYELLOW'),
(5, 'STARTRED'), --the tile where each color starts
(6, 'STARTBLUE'),
(7, 'STARTGREEN'),
(8, 'STARTYELLOW'),
(9, 'ENDRED'), --these are the final 7 coloured tiles where a piece ends up after a full circle to reach the goal
(10, 'ENDBLUE'),
(11, 'ENDGREEN'),
(12, 'ENDYELLOW'),
(13, 'GOALRED'), -- goal tile of each color
(14, 'GOALBLUE'),
(15, 'GOALGREEN'),
(16, 'GOALYELLOW');

INSERT INTO ParchisTile(id, tiletype_id, safe) VALUES
(1,1,false),
(2,1,false),
(3,1,false),
(4,1,false),
(5,5,true),
(6,1,false),
(7,1,false),
(8,1,false),
(9,2,false),
(10,2,false),
(11,2,false),
(12,2,true),
(13,2,false),
(14,2,false),
(15,2,false),
(16,2,false),
(17,2,true),

(18,2,false),
(19,2,false),
(20,2,false),
(21,2,false),
(22,6,true),
(23,2,false),
(24,2,false),
(25,2,false),
(26,3,false),
(27,3,false),
(28,3,false),
(29,3,true),
(30,3,false),
(31,3,false),
(32,3,false),
(33,3,false),
(34,3,true),

(35,3,false),
(36,3,false),
(37,3,false),
(38,3,false),
(39,7,true),
(40,3,false),
(41,3,false),
(42,3,false),
(43,4,false),
(44,4,false),
(45,4,false),
(46,4,true),
(47,4,false),
(48,4,false),
(49,4,false),
(50,4,false),
(51,4,true),

(52,4,false),
(53,4,false),
(54,4,false),
(55,4,false),
(56,8,true),
(57,4,false),
(58,4,false),
(59,4,false),
(60,1,false),
(61,1,false),
(62,1,false),
(63,1,true),
(64,1,false),
(65,1,false),
(66,1,false),
(67,1,false),
(68,1,true),

(69,9,false),
(70,9,false),
(71,9,false),
(72,9,false),
(73,9,false),
(74,9,false),
(75,9,false),
(76,13,false),

(77,10,false),
(78,10,false),
(79,10,false),
(80,10,false),
(81,10,false),
(82,10,false),
(83,10,false),
(84,14,false),

(85,11,false),
(86,11,false),
(87,11,false),
(88,11,false),
(89,11,false),
(90,11,false),
(91,11,false),
(92,15,false),

(93,12,false),
(94,12,false),
(95,12,false),
(96,12,false),
(97,12,false),
(98,12,false),
(99,12,false),
(100,16,false);








