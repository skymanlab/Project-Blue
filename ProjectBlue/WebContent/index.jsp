

<!-- 
     ========================================================================================
     Java code 
     ======================================================================================== 
  -->
<%@page import="Page.PageManager"%>
<%@page import="Session.SessionManager"%>
<%@page import="HttpResponse.ResponseUserInfoData"%>
<%@page import="HttpResponse.ResponseTokenData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
SessionManager sessionManager = null;

if (session.getAttribute("session_manager") == null) {
	response.sendRedirect("login.jsp");
	return; // servlet이 실행 중 이 문장 다음으로 실행이 되지 않고 서블릿 프로그램이 종료한다. 즉, 텅빈 문서가 되는 것이다.
} else {
	sessionManager = (SessionManager) session.getAttribute("session_manager");
}
%>


<!-- 
     ========================================================================================
     html start
     ======================================================================================== 
  -->
<!DOCTYPE html>
<html>


<!-- 									HEAD
     ========================================================================================
    1. viewport
    2. apple-touch-icon
    3. icon
    4. TITLE : SKYMAN LAB. by JDI
    5. googleapis / font
    6. fontawesome
    7. uncleo-icons
    8. black-dashboard:해당 템플릿을 적용하기 위한 css 파일 
    9. demo:dashboard에서 chart를 표현하기 위한 css 팡리
     ========================================================================================
 -->
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Project Blue Billiard</title>
<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">

<!-- Font Awesome 5 -->
<script src="https://kit.fontawesome.com/yourcode.js"></script>
</head>


<!-- 
     ========================================================================================
     javascript
     ======================================================================================== 
  -->
<!-- back button disable -->
<script type="text/javascript">
	function noBack() {
		window.history.forward();
	}
</script>


<!-- 										BODY
     ========================================================================================
     1. body / class / white-content:화면 전체 색을 흰색으로
     2. 순서 / wrapper ( sidebar -> content wrapper ( content ( sidebar -> container fluid ( page heading -> 내용 ) ) -> footer ) )
     3. 내용 위치:Content
     ========================================================================================
 -->
<body>

	<!-- [[warpper]] -->
	<div id="wrapper">

		<!-- sidebar -->
		<ul
			class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
			id="accordionSidebar"
		>
			<!-- Sidebar - Brand -->
			<a
				class="sidebar-brand d-flex align-items-center justify-content-center my-3"
				href=<%=PageManager.JSP_INDEX%>
			>
				<div class="sidebar-brand-icon rotate-n-15">
					<img
						src="./img/logoANDicon/07 LOGO_white_object_file.png"
						style="height: 40px;"
					>
				</div>
				<div class="sidebar-brand-text mx-3">Project Blue</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">

			<!-- sidebar item 1. Main -->
			<li class="nav-item active"><a
				class="nav-link"
				href=<%=PageManager.JSP_INDEX%>
			> <i class="fas fa-fw fa-tachometer-alt"></i> <span>Main</span>
			</a></li>

			<!-- Divider -->
			<hr class="sidebar-divider ">
			<!-- sidebar heading 1. information -->
			<div class="sidebar-heading">Infomation</div>
			<!-- sidebar item 1-1. user info menu -->
			<li class="nav-item"><a
				class="nav-link collapsed"
				href="#"
				data-toggle="collapse"
				data-target="#collapseOne"
				aria-expanded="true"
				aria-controls="collapseOne"
			> <i class="fas fa-user-circle"></i> <span>User Info</span>
			</a>
				<div
					id="collapseOne"
					class="collapse"
					aria-labelledby="headingOne"
					data-parent="#accordionSidebar"
				>
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">User Info.:</h6>
						<a
							class="collapse-item"
							href=<%=PageManager.JSP_USER_INFO%>
						>회원 정보</a> <a
							class="collapse-item"
							href="#"
						>회원 정보 수정</a>
					</div>
				</div></li>
			<!-- sidebar item 1-2. billiard info menu -->
			<li class="nav-item"><a
				class="nav-link collapsed"
				href="#"
				data-toggle="collapse"
				data-target="#collapseTwo"
				aria-expanded="true"
				aria-controls="collapseTwo"
			> <i class="	fas fa-hand-point-right"></i> <span>Billiard</span>
			</a>
				<div
					id="collapseTwo"
					class="collapse"
					aria-labelledby="headingTwo"
					data-parent="#accordionSidebar"
				>
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">Billiard info.:</h6>
						<a
							class="collapse-item"
							href=<%=PageManager.JSP_BILLIARD%>
						>MAIN</a> <a
							class="collapse-item"
							href="#"
						>통계정보</a>
					</div>
				</div></li>
			<!-- ./End of sidebar heading 1 -->

			<!-- Divider -->
			<hr class="sidebar-divider">
			<!-- sidebar heading 2. application-->
			<div class="sidebar-heading">Application</div>
			<!-- sidebar item 2-1. logout -->
			<li class="nav-item active"><a
				class="nav-link"
				href="#"
				data-toggle="modal"
				data-target="#logoutModal"
			> <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> <span>Logout</span>
			</a></li>
			<!-- ./End of sidebar heading 2 -->

			<!-- Divider -->
			<hr class="sidebar-divider">
			<!-- sidebar heading 3. test-->
			<div class="sidebar-heading">TEST</div>
			<!-- sidebar item 3-1. test page-->
			<li class="nav-item"><a
				class="nav-link"
				href=<%=PageManager.JSP_TEST_PAGE %>
			> <i class="fas fa-fw fa-tachometer-alt"></i> <span>Test Page</span>
			</a></li>
			<!-- sidebar item 3-2. page format -->
			<li class="nav-item "><a
				class="nav-link"
				href=<%=PageManager.JSP_PAGE_FORMAT %>
			> <i class="fas fa-fw fa-tachometer-alt"></i> <span>Page Format</span>
			</a></li>
			<!-- ./End of sidebar heading 3 -->

			<!-- Divider -->
			<hr class="sidebar-divider ">
			<!-- Sidebar Toggler (Sidebar) -->
			<div class="text-center d-none d-md-inline">
				<button
					class="rounded-circle border-0"
					id="sidebarToggle"
				></button>
			</div>
		</ul>
		<!-- ./End of Sidebar -->


		<!-- coontent wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
		
			<!-- content -->
			<div id="content">

				<!-- topbar -->
				<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
					<!-- Sidebar Toggle (Topbar) -->
					<button
						id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3"
					>
						<i class="fa fa-bars"></i>
					</button>

					<!-- topbar search -->
					<form
						class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search"
					>
						<div class="input-group">
							<input
								type="text"
								class="form-control bg-light border-0 small"
								placeholder="Search for..."
								aria-label="Search"
								aria-describedby="basic-addon2"
							>
							<div class="input-group-append">
								<button
									class="btn btn-primary"
									type="button"
								>
									<i class="fas fa-search fa-sm"></i>
								</button>
							</div>
						</div>
					</form>
					<!-- ./End of topbar search -->

					<!-- topbar navbar -->
					<ul class="navbar-nav ml-auto">
						<!-- navbar item 1. search dropdown  -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a
							class="nav-link dropdown-toggle"
							href="#"
							id="searchDropdown"
							role="button"
							data-toggle="dropdown"
							aria-haspopup="true"
							aria-expanded="false"
						> <i class="fas fa-search fa-fw"></i>
						</a>
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown"
							>
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input
											type="text"
											class="form-control bg-light border-0 small"
											placeholder="Search for..."
											aria-label="Search"
											aria-describedby="basic-addon2"
										>
										<div class="input-group-append">
											<button
												class="btn btn-primary"
												type="button"
											>
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>

						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- navbar item 2. user infomation  -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle"
							href="#"
							id="userDropdown"
							role="button"
							data-toggle="dropdown"
							aria-haspopup="true"
							aria-expanded="false"
						> <!-- 사용자 프로필 가져오기  --> <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=sessionManager.getUserInfo().getProperties().getNickname()%></span>
								<img
								class="img-profile rounded-circle"
								src=<%=sessionManager.getUserInfo().getKakao_accout().getProfile().getThumbnail_image()%>
							>
						</a>
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown"
							>

								<a
									class="dropdown-item"
									href=<%=PageManager.JSP_USER_INFO %>
								> <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> 회원 정보
								</a> <a
									class="dropdown-item"
									href="#"
								> <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i> 회원 정보 수정
								</a>
								<div class="dropdown-divider"></div>
								<a
									class="dropdown-item"
									href="#"
									data-toggle="modal"
									data-target="#logoutModal"
								> <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Logout
								</a>
							</div></li>
					</ul>
					<!-- ./End of topbar navbar -->
				</nav>
				<!-- ./End of topbar -->


				<!-- container fluid -->
				<div class="container-fluid">

					<!-- page heading -->
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">Main</h1>
						<a href="#"
							class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
							class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>
					</div>
					<!-- ./End of page heading -->



					<!-- 						내용 시작
						=========================================================== 
												
						===========================================================
					-->
					<!-- Content Row 0-->
					<div class="row">
						<!-- Area Chart -->
						<div class="col-xl-12 col-lg-12">
							<div class="card shadow mb-4">
								<!-- Card Header - Dropdown -->
								<div
									class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
									<h6 class="m-0 font-weight-bold text-primary ">Billiard</h6>
									<a href="./billiard.jsp" class="d-none d-sm-inline-block"><i
										class="fas fa-download fa-sm text-white-50 right"></i> 더보기</a>
								</div>

								<div class="card-body">

									<!-- Content Row 01-->
									<div class="row">
										<!-- 최근 경기 날짜 -->
										<div class="col-xl-4 col-md-12 mb-4">
											<div class="card border-left-primary shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">날짜</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800">8월
																30일</div>
														</div>
														<div class="col-auto">
															<i class="fas fa-calendar fa-2x text-gray-300"></i>
														</div>
													</div>
												</div>
											</div>
										</div>

										<!-- 최근 경기 승자 -->
										<div class="col-xl-4 col-md-12 mb-4">
											<div class="card border-left-primary shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">승자</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800">장동익</div>
														</div>
														<div class="col-auto">
															<i class="fas fa-calendar fa-2x text-gray-300"></i>
														</div>
													</div>
												</div>
											</div>
										</div>

										<!-- 최근 경기 가격 -->
										<div class="col-xl-4 col-md-12 mb-4">
											<div class="card border-left-primary shadow h-100 py-2">
												<div class="card-body">
													<div class="row no-gutters align-items-center">
														<div class="col mr-2">
															<div
																class="text-xs font-weight-bold text-primary text-uppercase mb-1">가격</div>
															<div class="h5 mb-0 font-weight-bold text-gray-800">12,000원</div>
														</div>
														<div class="col-auto">
															<i class="fas fa-calendar fa-2x text-gray-300"></i>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- ./Content Row 01-->
								</div>

							</div>
						</div>
					</div>
					<!-- ./Content Row 0-->
					<!-- 						내용 끝
						=========================================================== 
												
						===========================================================
					-->

				</div>
				<!-- ./End of container fluid -->
			</div>
			<!-- End of content -->

			<!-- footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; SKYMAN lab. - Project Blue Billiard</span>
					</div>
				</div>
			</footer>
			<!-- ./End of footer -->

		</div>
		<!-- ./End of content wrapper -->
	</div>
	<!-- ./End of [[wrapper]] -->



	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>
	<!-- ./End of Scroll to Top Button-->


	<!-- logout modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="./LogoutServlet">Logout</a>
				</div>
			</div>
		</div>
	</div>
	<!-- ./End of logout modal -->


	<!-- 								BODY Script
    ========================================================================================
    1. Bootstrap core JavaScript
    2. Core plugin JavaScript
    3. Custom scripts for all pages
    4. Page level plugins
    5. Page level custom scripts
    6. 뒤로 가기 막기
    ========================================================================================
     -->
	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript -->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages -->
	<script src="js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script src="vendor/chart.js/Chart.min.js"></script>

	<!-- Page level custom scripts -->
	<script src="js/demo/chart-area-demo.js"></script>
	<script src="js/demo/chart-pie-demo.js"></script>

	<!-- 뒤로 가기 막기 -->
	<script>
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>
</body>
</html>