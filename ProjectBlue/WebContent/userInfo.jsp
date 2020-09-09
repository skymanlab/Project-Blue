<%@page import="HttpResponse.ResponseUserInfoData"%>
<%@page import="HttpResponse.ResponseTokenData"%>
<%@ page
     language="java"
     contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>


<!--                    java code               -->
<%
	ResponseTokenData responseTokenData = null;
ResponseUserInfoData responseUserInfoData = null;

if (session.getAttribute("session_token") == null) {

	response.sendRedirect("login.jsp");
	return; // servlet이 실행 중 이 문장 다음으로 실행이 되지 않고 서블릿 프로그램이 종료한다. 즉, 텅빈 문서가 되는 것이다.
} else {
	responseTokenData = (ResponseTokenData) session.getAttribute("session_token");
	responseUserInfoData = (ResponseUserInfoData) session.getAttribute("session_user_info");
}
%>


<!--					head					-->
<head>
<meta charset="UTF-8">
<meta
     http-equiv="X-UA-Compatible"
     content="IE=edge"
>
<meta
     name="viewport"
     content="width=device-width, initial-scale=1, shrink-to-fit=no"
>
<meta
     name="description"
     content=""
>
<meta
     name="author"
     content=""
>
<title>Project Blue Billiard</title>

<!-- Custom fonts for this template-->
<link
     href="vendor/fontawesome-free/css/all.min.css"
     rel="stylesheet"
     type="text/css"
>
<link
     href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
     rel="stylesheet"
>

<!-- Custom styles for this template-->
<link
     href="css/sb-admin-2.min.css"
     rel="stylesheet"
>
</head>


<!--	               script	                  -->
<!-- back button disable -->
<script type="text/javascript">
    function noBack() {
	window.history.forward();
    }
</script>

<!-- Font Awesome 5 -->
<script src="https://kit.fontawesome.com/yourcode.js"></script>



<!--	               body	                      -->
<body onload="">

     <!-- Page Wrapper -->
     <div id="wrapper">

           <!-- Sidebar -->
          <ul
               class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
               id="accordionSidebar"
          >
               <!-- Sidebar - Brand -->
               <a
                    class="sidebar-brand d-flex align-items-center justify-content-center my-3"
                    href="index.jsp"
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

               <!-- Nav Item - 1. Main -->
               <li class="nav-item active"><a
                    class="nav-link"
                    href="index.jsp"
               > <i class="fas fa-fw fa-tachometer-alt"></i> <span>Main</span></a></li>


               <!-- Divider -->
               <hr class="sidebar-divider ">

               <!-- Heading 1. information -->
               <div class="sidebar-heading">Infomation</div>

               <!-- Nav Item 1-1. user info menu -->
               <li class="nav-item"><a
                    class="nav-link collapsed"
                    href="#"
                    data-toggle="collapse"
                    data-target="#collapseOne"
                    aria-expanded="true"
                    aria-controls="collapseOne"
               > <i class="fas fa-user-circle"></i> <span>User Info</span></a>
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
                                   href="userInfo.jsp"
                              >회원 정보</a> <a
                                   class="collapse-item"
                                   href="#"
                              >회원 정보 수정</a>
                         </div>
                    </div></li>

               <!-- Nav Item 1-2. billiard info menu -->
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
                                   href="billiard.jsp"
                              >MAIN</a> <a
                                   class="collapse-item"
                                   href="#"
                              >통계정보</a>
                         </div>
                    </div></li>
               <!-- ./End of Heading 1. information -->


               <!-- Divider -->
               <hr class="sidebar-divider">

               <!-- Heading 2. application-->
               <div class="sidebar-heading">Application</div>

               <!-- Nav Item - 2-1. logout -->
               <li class="nav-item active"><a
                    class="nav-link"
                    href="#"
                    data-toggle="modal"
                    data-target="#logoutModal"
               > <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> <span>Logout</span></a></li>
               <!-- ./End of Heading 2. application-->


               <!-- Divider -->
               <hr class="sidebar-divider">

               <!-- Heading 3. test-->
               <div class="sidebar-heading">TEST</div>

               <!-- Nav Item 3-1. test page-->
               <li class="nav-item"><a
                    class="nav-link"
                    href="testpage.jsp"
               > <i class="fas fa-fw fa-tachometer-alt"></i> <span>Test Page</span></a></li>

               <!-- Nav Item 3-2. page format -->
               <li class="nav-item "><a
                    class="nav-link"
                    href="pageformat.jsp"
               > <i class="fas fa-fw fa-tachometer-alt"></i> <span>Page Format</span></a></li>
               <!-- ./End of Heading 3. test-->


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
          <!-- End of Sidebar -->
          
          

          <!-- Content Wrapper -->
          <div
               id="content-wrapper"
               class="d-flex flex-column"
          >

               <!-- Main Content -->
               <div id="content">

                    <!-- Topbar -->
                    <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                         <!-- Sidebar Toggle (Topbar) -->
                         <button
                              id="sidebarToggleTop"
                              class="btn btn-link d-md-none rounded-circle mr-3"
                         >
                              <i class="fa fa-bars"></i>
                         </button>

                         <!-- Topbar Search -->
                         <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
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

                         <!-- Topbar Navbar -->
                         <ul class="navbar-nav ml-auto">

                              <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                              <li class="nav-item dropdown no-arrow d-sm-none"><a
                                   class="nav-link dropdown-toggle"
                                   href="#"
                                   id="searchDropdown"
                                   role="button"
                                   data-toggle="dropdown"
                                   aria-haspopup="true"
                                   aria-expanded="false"
                              > <i class="fas fa-search fa-fw"></i>
                              </a> <!-- Dropdown - Messages -->
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

                              <!-- Nav Item - User Information -->
                              <li class="nav-item dropdown no-arrow"><a
                                   class="nav-link dropdown-toggle"
                                   href="#"
                                   id="userDropdown"
                                   role="button"
                                   data-toggle="dropdown"
                                   aria-haspopup="true"
                                   aria-expanded="false"
                              > <!-- 사용자 프로필 가져오기  --> <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=responseUserInfoData.getProperties().getNickname()%></span> <img
                                        class="img-profile rounded-circle"
                                        src=<%=responseUserInfoData.getKakao_accout().getProfile().getThumbnail_image()%>
                                   >
                              </a> <!-- Dropdown - User Information -->
                                   <div
                                        class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                        aria-labelledby="userDropdown"
                                   >

                                        <a
                                             class="dropdown-item"
                                             href="userInfo.jsp"
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

                    </nav>
                    <!-- End of Topbar -->



                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                         <!-- Page Heading -->
                         <div class="d-sm-flex align-items-center justify-content-between mb-4">
                              <h1 class="h3 mb-0 text-gray-800">회원 정보</h1>
                              <a
                                   href="#"
                                   class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"
                              ><i class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>
                         </div>

                         <!-- Content Row 1-->
                         <div class="row">
                              <!-- User Info. : nickname -->
                              <div class="col-xl-3 col-md-6 mb-4">
                                   <div class="card border-left-primary shadow h-100 py-2">
                                        <div class="card-body">
                                             <div class="row no-gutters align-items-center">
                                                  <div class="col mr-2">
                                                       <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">닉네임</div>
                                                       <div class="h5 mb-0 font-weight-bold text-gray-800"><%=responseUserInfoData.getProperties().getNickname()%></div>
                                                  </div>
                                                  <div class="col-auto">
                                                       <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                                  </div>
                                             </div>
                                        </div>
                                   </div>
                              </div>

                              <!-- User Info. : profile -->
                              <div class="col-xl-3 col-md-6 mb-4">
                                   <div class="card border-left-primary shadow h-100 py-2">
                                        <div class="card-body">
                                             <div class="row no-gutters align-items-center">
                                                  <div class="col mr-2">
                                                       <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">프로필 사진</div>
                                                       <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                            <img src=<%=responseUserInfoData.getProperties().getProfile_image()%>>
                                                       </div>
                                                  </div>
                                                  <div class="col-auto">
                                                       <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                                  </div>
                                             </div>
                                        </div>
                                   </div>
                              </div>
                         </div>
                         <!-- /.Content Row 1 -->
                    </div>
                    <!-- /.container-fluid -->

               </div>
               <!-- End of Main Content -->

               <!-- Footer -->
               <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                         <div class="copyright text-center my-auto">
                              <span>Copyright &copy; SKYMAN lab. - Project Blue Billiard</span>
                         </div>
                    </div>
               </footer>
               <!-- End of Footer -->

          </div>
          <!-- End of Content Wrapper -->

     </div>
     <!-- End of Page Wrapper -->

     <!-- Scroll to Top Button-->
     <a
          class="scroll-to-top rounded"
          href="#page-top"
     > <i class="fas fa-angle-up"></i>
     </a>

     <!-- Logout Modal-->
     <div
          class="modal fade"
          id="logoutModal"
          tabindex="-1"
          role="dialog"
          aria-labelledby="exampleModalLabel"
          aria-hidden="true"
     >
          <div
               class="modal-dialog"
               role="document"
          >
               <div class="modal-content">
                    <div class="modal-header">
                         <h5
                              class="modal-title"
                              id="exampleModalLabel"
                         >Ready to Leave?</h5>
                         <button
                              class="close"
                              type="button"
                              data-dismiss="modal"
                              aria-label="Close"
                         >
                              <span aria-hidden="true">×</span>
                         </button>
                    </div>
                    <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                    <div class="modal-footer">
                         <button
                              class="btn btn-secondary"
                              type="button"
                              data-dismiss="modal"
                         >Cancel</button>
                         <a
                              class="btn btn-primary"
                              href="./LogoutServlet"
                         >Logout</a>
                    </div>
               </div>
          </div>
     </div>

     <!-- Bootstrap core JavaScript-->
     <script src="vendor/jquery/jquery.min.js"></script>
     <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

     <!-- Core plugin JavaScript-->
     <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

     <!-- Custom scripts for all pages-->
     <script src="js/sb-admin-2.min.js"></script>

     <!-- Page level plugins -->
     <script src="vendor/chart.js/Chart.min.js"></script>

     <!-- Page level custom scripts -->
     <script src="js/demo/chart-area-demo.js"></script>
     <script src="js/demo/chart-pie-demo.js"></script>
     <script>
			history.pushState(null, null, location.href);
			window.onpopstate = function() {
			    history.go(1);
			};
		    </script>
</body>
</html>