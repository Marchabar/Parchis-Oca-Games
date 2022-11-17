<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, Oca Lobbies, vets or error"%>

	
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<ocaParchis:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</ocaParchis:menuItem>

				<sec:authorize access="hasAuthority('admin')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> Admin
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<ocaParchis:menuItem active="${name eq 'Oca Lobbies'}" url="/lobbies"
								title="All Lobbies">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>All Lobbies</span>
								</ocaParchis:menuItem>
							</li>
							<li class="divider"></li>							
                            <li> 
								<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/users"
								title="Manage users">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Manage users</span>
								</ocaParchis:menuItem>
							</li>
							<li class="divider"></li>							
                            <li> 
								<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/friends"
								title="All Friends">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>All Friends</span>
								</ocaParchis:menuItem>
							</li>
						</ul></li>
				</sec:authorize>
				
				<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> Lobbies
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<ocaParchis:menuItem active="${name eq 'Oca Lobbies'}" url="/lobbies/oca"
								title="Oca Lobbies">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Oca Lobbies</span>
								</ocaParchis:menuItem>
							</li>
							<li class="divider"></li>							
                            <li> 
								<ocaParchis:menuItem active="${name eq 'Parchis Lobbies'}" url="/lobbies/parchis"
								title="Parchis Lobbies">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								<span>Parchis Lobbies</span>
								</ocaParchis:menuItem>
							</li>
						</ul></li>
			
				<ocaParchis:menuItem active="${name eq 'My friends'}" url="/friends/myfriends"
					title="My friends">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>My friends</span>
				</ocaParchis:menuItem>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> Stats
							<span class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<ocaParchis:menuItem active="${name eq 'My stats'}" url="/playerstats"
								title="My stats">
								<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
								<span>My stats</span>
								</ocaParchis:menuItem>
							</li>
							<li class="divider"></li>							
                            <li> 
								<ocaParchis:menuItem active="${name eq 'Global Stats'}" url="/playerstats/global"
								title="Global Stats">
								<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
								<span>Global</span>
								</ocaParchis:menuItem>
							</li>
						</ul></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/register" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
