<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
<title>Ranking</title>
</head>
<body style="background-color:#ececec">
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close"> </a>
		</div>
		</c:if>
	</div>
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
	<c:if test="${currentLobby != null}">
	<div style="float:right">
		<a class="btn btn-danger" href="/lobbies/${currentLobby.id}">Go back to lobby</a><br><br>
	</div>
</c:if>
<<<<<<< HEAD
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
	<table class="table table-striped">
		<c:if test="${param.wins}">
			<h2 style="font-family:monospace;">Ranking by Wins:</h2>
		<tr>	
			<th>Ranking Number</th>	
			<th>Player</th>
			<th>Number of Wins</th>
		</tr>
		<c:forEach items="${winners}"  var="winners" varStatus="status">
			<tr>
				<c:if test= "${status.index+1 == 1}">
					<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
					<td style="color:#958112"><c:out value = "${winners}"/></td>
				</c:if>
				<c:if test= "${status.index+1 == 2}">
					<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
					<td style="color:#808080"><c:out value = "${winners}"/></td>
				</c:if>
				<c:if test= "${status.index+1 == 3}">
					<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
					<td style="color:#804614"><c:out value = "${winners}"/></td>
				</c:if>
				<c:if test= "${status.index+1 > 3}">
					<td><c:out value = "${status.index+1}º"/></td>
					<td><c:out value = "${winners}"/></td>
				</c:if>
				<td><c:out value = "${wins[status.index]}"/></td>
			</tr>
		</c:forEach>
		</c:if>
		<c:if test="${param.stuck}">
			<h2 style="font-family:monospace;">Ranking by Turns Stuck:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Number of Turns Stuck</th>
			</tr>
			<c:forEach items="${rankingByNameTurnStuck}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countTurnStuck[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${param.goose}">
			<h2 style="font-family:monospace;">Ranking by Times Fell in Goose Tile:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Ranking by Times Fell in Goose Tile</th>
			</tr>
			<c:forEach items="${rankingByGoose}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countGoose[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${param.well}">
			<h2 style="font-family:monospace;">Ranking by Times Fell in Well Tile:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Ranking by Times Fell in Well Tile</th>
			</tr>
			<c:forEach items="${rankingByWell}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countWell[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${param.labyrinth}">
			<h2 style="font-family:monospace;">Ranking by Times Fell in Labyrinth Tile:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Ranking by Times Fell in Labyrinth Tile</th>
			</tr>
			<c:forEach items="${rankingByLabyrinth}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countLabyrinth[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${param.prison}">
			<h2 style="font-family:monospace;">Ranking by Times Fell in Prison Tile:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Ranking by Times Fell in Prison Tile</th>
			</tr>
			<c:forEach items="${rankingByPrison}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countPrison[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${param.death}">
			<h2 style="font-family:monospace;">Ranking by Times Fell in Death Tile:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Ranking by Times Fell in Death Tile</th>
			</tr>
			<c:forEach items="${rankingByDeath}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countDeath[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${param.inn}">
			<h2 style="font-family:monospace;">Ranking by Times Fell in Inn Tile:</h2>
			<tr>	
				<th>Ranking Number</th>	
				<th>Player</th>
				<th>Ranking by Times Fell in Inn Tile</th>
			</tr>
			<c:forEach items="${rankingByDeath}"  var="winners" varStatus="status">
				<tr>
					<c:if test= "${status.index+1 == 1}">
						<td style="color:#958112"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#958112"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 2}">
						<td style="color:#808080"><c:out value = "${status.index+1}º"/></td>
						<td style="color:#808080"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 == 3}">
						<td style="color:#804614" ><c:out value = "${status.index+1}º"/></td>
						<td style="color:#804614"><c:out value = "${winners}"/></td>
					</c:if>
					<c:if test= "${status.index+1 > 3}">
						<td><c:out value = "${status.index+1}º"/></td>
						<td><c:out value = "${winners}"/></td>
					</c:if>
					<td><c:out value = "${countInn[status.index]}"/></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<div style="display: inline-block; width: 100%;">
		<div style="float:right">
			<div class="btn btn-danger">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" style="color:white">Filter
						<span class="glyphicon glyphicon-chevron-down"></span>
				</a>
					<ul class="dropdown-menu">
						<li>
							<ocaParchis:menuItem active="${name eq 'Wins'}" url="/playerstats/ranking?wins=true"
							title="Wins">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Wins</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?stuck=true"
							title="Stuck">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Stuck</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?goose=true"
							title="Goose">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Goose</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?well=true"
							title="Well">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Well</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?labyrinth=true"
							title="Labyrinth">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Labyrinth</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?prison=true"
							title="Prison">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Prison</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?death=true"
							title="Death">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Death</span>
							</ocaParchis:menuItem>
						</li>
						<li class="divider"></li>							
						<li> 
							<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/playerstats/ranking?inn=true"
							title="Inn">
							<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
							<span>Inn</span>
							</ocaParchis:menuItem>
						</li>
					</ul></li>
			</div>
		</div>
	</div>
</body>
</ocaParchis:layout>
</html>