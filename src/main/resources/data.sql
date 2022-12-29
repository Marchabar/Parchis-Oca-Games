INSERT INTO status VALUES
(1,'Online'),
(2,'Offline'),
(3,'Away');

INSERT INTO games VALUES
(1, 'Oca'),
(2, 'Parchis');

INSERT INTO colors (id, name, rgb) VALUES
(1, 'RED','#f43e3e'),
(2, 'BLUE','#3d5cf9'),
(3, 'GREEN','#4c9c24'),
(4, 'YELLOW','#bf870f');

INSERT INTO User(id, login, password, status_id, role, prefcolor_id) VALUES
(1, 'pepito', '$2y$10$AnTs0JSo4ifIyJxhi/uEsOLoN4jkLvDF/R4/8PSYjHoGuecFOHKLi', 1, 'admin',2),
(2, 'Roll20_2DS', '$2y$10$Ky3jcJX1oGeDZhXfp4.Dcu.kn9gejHm.QTH0/gB5Z3/eHkT5JehfK', 1, 'member',4),
(3, 'DeOcaEnOca', '$2y$10$VmzfLfnRdOYQKdwz2uie8uDLrzsVbBl9pQ7gE9MsqCExMkb4.OrOO', 1,  'member',1),
(4, 'luke1', '$2y$10$nKau3KxL4hylcl9XYqP5Pun8Pq8x5Bz8L0ePfkH0Qf2jeHJQmAkje', 1,  'member',2),
(5, 'susato', '$2y$10$u98NB2OUIQUoe3Hlt2svhear7WMYN70KigA.0OJecWIAtWoatws.6', 1,  'member',2),
(6, 'josemicrack', '$2y$10$h45oF7U1PPhVDVNaD79ZguinfN6rOVGdoo1e0nMiG5OIpod8WdJvC', 1,  'member',2),
(7, 'josemiidolo', '$2y$10$3RcCPhxqZUJS/Bk6yxVGp.nyjzp9iCB0HldlP15tcSso.tUdqPe.6', 3,  'member',3),
(8, 'josemimastodonte', '$2y$10$dkG/UMaXrGAf/eQGGgJO7u1MsboJqdPjWt2DooC0mq1kZHuP7t.zC', 1,  'member',2),
(9, 'josemifiera', '$2y$10$4J5mbXKeegM0O0qv/1jYLOjDE9j6WJzdQY07bZdA.YGjwtTV/G9La', 1,  'member',3),
(10, 'Xx_casa777rexpro_xX', '$2y$10$zk78IuNE.6tAwlHyAbqtiuCrB5Ms032edEA2ZqERey03GeHlu6Wry', 2,  'member',3),
(11, 'mashedpotato', '$2y$10$mpFhBNbgXjPX3rsbLPdr1emTDtFfpPRUOFbdkYj3YsRyrex.UBefO', 1,  'member',3),
(12, 'pisten', '$2y$10$quMOZjf.ecqO5D5oozju2u2MBAPyMDfxYfVwbspah7IWha8/1m9d2', 1,  'member',3),
(13, 'cortat23', '$2y$10$zHcudOaCmzrFAVcGYfaRhOPfVgkL8RAbPDsQIQ2B7zNpv1avRh9NK', 1,  'member',3),
(14, 'cookiecliker1', '$2y$10$NbKlxSDCcegyrpIIHZTw2ufYcrYHG5qGnQj5RQ9WbzLWGm5NjN2da', 3,  'member',4),
(15, '123', '$2y$10$Qep/M7DLIqotunK9Kkl8auXVtoHMSbrFZuFmE63Q4ApVM.vjQ/2Fy',1,'member',4),
(16, 'offlineguy', '$2y$10$OM4u6hvgshxZQWC84py7Nexx044jhft5HKkQsdWJOAjQQpzGEM/TG', 2, 'member',4),
(17, 'awayguy', '$2y$10$P8uh/JypcRC64cwVjssh1Ow.M1otsgSeUhaybTnZvHWk.mIeEnyEa', 3, 'member',4);

INSERT INTO Friend(id, User1_id, User2_id, solicitingUser_id, accept, dateF) VALUES
(1, 1, 6,1,1,'2022-03-10'),
(2, 1, 7,1,1,'2022-03-09'),
(3, 1, 8,1,1,'2022-03-08'),
(4, 1, 9,1,1,'2022-03-07'),
(5, 1, 3,1,1,'2022-03-07'),
(6, 1, 4,1,1,'2022-03-07'),
(7, 1, 16,1,1,'2022-03-06'),
(8, 1, 17,1,1,'2022-03-06');



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

INSERT INTO Playerstats(id, numDiceRolls, playerColor_id, user_id, position,turnsStuck, 
numberOfGooses, numberOfPlayerWells,numberOfLabyrinths,numberOfPlayerPrisons,numberOfPlayerDeaths,numberOfInns) VALUES 
(1,  23, 1 ,1, 63, 0, 2, 1, 1, 0, 0,0),
(2, 12, 2, 2, 60,0, 3, 2, 1, 1, 0,0),
(3, 30, 1, 1, 63,0, 1, 0, 2, 1, 0,0),
(4,  35, 2, 2, 13,0, 4, 0, 0, 1, 1,0),
(5,  32, 3 ,1, 42,0, 2, 0, 2, 0, 0,0),
(6,  15, 2, 3, 63,0, 0, 2, 0, 0, 0,0),
(7,  84, 1, 4, 2,0, 5, 1, 1, 0, 1,0),
(8,  54, 2, 5, 63,0, 8, 0, 1, 2, 1,0),
(9,  54, 4, 6, 23,0, 8, 0, 1, 2, 1,0),
(10,  54, 4, 3, 23,0, 8, 0, 1, 2, 1,0),
(11,  54, 1, 4, 23,0, 8, 0, 1, 2, 1,0);

INSERT INTO Achievement(id, name, description, fileImage) VALUES
(1, 'Roller I', 'Roll the dice up to 1 points', 'roller1'),
(2, 'Roller II', 'Roll the dice up to 10 points', 'roller2'),
(3, 'Roller III', 'Roll the dice up to 100 points', 'roller3'),
(4, 'Roller IV', 'Roll the dice up to 1000 points', 'roller4');

INSERT INTO Match(id,game_id,numTurns,winner_id, lastRoll, numMatchKills,
numMatchBarriers,numMatchSpecialTiles,totalDistanceGooses,lobby_id,playertoplay_id) VALUES
(1,1,0,1,0,null,null,11,5,1,1),
(2,1,7,3,65,null,null,10,5,1,2),
(3,1,0,6,0,null,null,0,0,3,1),
(4,1,0,8,0,null,null,0,0,4,1),
(5,1,0,null,0,null,null,0,0,4,1);

INSERT INTO Match_Playerstats(match_id, playerstats_id) VALUES
(1,1),
(1,2),
(2,3),
(2,4),
(3,5),
(3,6),
(4,7),
(4,8),
(4,9),
(5,10),
(5,11);

INSERT INTO MessageChat(id, description, time, match_id, user_id) VALUES
(1,'GG guys', '15:55:10', 1, 1),
(2, 'well played', '15:56:12', 1, 1),
(3,'hey', '17:55:10', 5, 3),
(4, 'two ocas lmao', '17:56:12', 5, 4),
(5, 'ikr', '17:57:16', 5, 3);


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
(1, 'NORMALRED'), --the normal tiles where everyone can go. the fifth tile is their start
(2, 'NORMALBLUE'),
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
(9,1,false),
(10,1,false),
(11,1,false),
(12,1,true),
(13,1,false),
(14,1,false),
(15,1,false),
(16,1,false),
(17,1,true),

(18,2,false),
(19,2,false),
(20,2,false),
(21,2,false),
(22,6,true),
(23,2,false),
(24,2,false),
(25,2,false),
(26,2,false),
(27,2,false),
(28,2,false),
(29,2,true),
(30,2,false),
(31,2,false),
(32,2,false),
(33,2,false),
(34,2,true),

(35,3,false),
(36,3,false),
(37,3,false),
(38,3,false),
(39,7,true),
(40,3,false),
(41,3,false),
(42,3,false),
(43,3,false),
(44,3,false),
(45,3,false),
(46,3,true),
(47,3,false),
(48,3,false),
(49,3,false),
(50,3,false),
(51,3,true),

(52,4,false),
(53,4,false),
(54,4,false),
(55,4,false),
(56,8,true),
(57,4,false),
(58,4,false),
(59,4,false),
(60,4,false),
(61,4,false),
(62,4,false),
(63,4,true),
(64,4,false),
(65,4,false),
(66,4,false),
(67,4,false),
(68,4,true),

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
(100,13,false);








