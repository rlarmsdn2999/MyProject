<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Scrolling Nav - Start Bootstrap Template</title>
<link rel="icon" type="image/x-icon"
	href="/resources/assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="/resources/css/styles.css" rel="stylesheet" />
</head>
<body id="page-top">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.min.js"></script>
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"
		id="mainNav">
		<div class="container px-4">
			<a class="navbar-brand" href="#page-top">Start Bootstrap</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="#about">About</a></li>
					<li class="nav-item"><a class="nav-link" href="#services">Services</a></li>
					<li class="nav-item"><a class="nav-link" href="#contact">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- Header-->
	<header class="bg-primary bg-gradient text-white">
		<div class="container px-4 text-center">
			<h1 class="fw-bolder">Welcome to MyBoard</h1>
			<a class="btn btn-lg btn-light" href="#about">Start scrolling!</a>
		</div>
	</header>
	<!-- About section-->
	<section id="about">
		<div class="container px-4">
			<div class="row gx-4 justify-content-center">
				<div class="col-lg-8">
					<h2>CONTENT</h2>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="board">
								<tr>
									<td>${board.bno}</td>
									<td><a class="move" href='<c:out value="${board.bno}"/>'><c:out value="${board.title}"/></a></td>
									<td>${board.writer}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${board.regdate}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class='row'>
						<div class="col-lg-12">
							<form id="searchForm" action="/board/list" method="get">
								<select name="type">
									<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected':''}" />>---</option>
									<option value="T" <c:out value="${pageMaker.cri.type eq 'T' ? 'selected':''}" />>제목</option>
									<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected':''}" />>내용</option>
									<option value="W" <c:out value="${pageMaker.cri.type eq 'W' ? 'selected':''}" />>작성자</option>
									<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected':''}" />>제목 or 내용</option>
									<option value="TW" <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected':''}" />>제목 or 작성자</option>
									<option value="WC" <c:out value="${pageMaker.cri.type eq 'WC' ? 'selected':''}" />>내용 or 작성자</option>
									<option value="TCW" <c:out value="${pageMaker.cri.type eq 'TCW' ? 'selected':''}" />>제목 or 내용 or 작성자</option>
								</select>
								<input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>'/>
								<input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>'/>
								<input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>'/>
								<button class="btn btn-default">검색</button>
							</form>
						</div>
					</div>
					<form id="actionForm" action="/board/list" method="get">
						<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
						<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
						<input type='hidden' name='type' value='${pageMaker.cri.type}'>
						<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
					</form>
					<div class='pull-right' >
						<ul class="pagination"  id="pagination">
							<c:if test="${pageMaker.prev}">
								<li class="paginate_button previous"><a href="${pageMaker.startPage-1}">이전</a></li>
							</c:if>
							
							<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
								<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""}"><a href="${num}">${num}</a></li>
							</c:forEach>
							
							<c:if test="${pageMaker.next}">
								<li class="paginate_button next"><a href="${pageMaker.endPage +1}">다음</a></li>
							</c:if>
						</ul>
					</div>
					<div>
						<button type="button" class="btn btn-sm btn-primary"
							id="btnWriteForm">글쓰기</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">Modal Title</h4>
							</div>
							<div class="modal-body">처리가 완료되었습니다.</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save Changes</button>
							</div>	
						</div>
					</div>
				</div>
	</section>
	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container px-4">
			<p class="m-0 text-center text-white">Copyright &copy; Your
				Website 2022</p>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="/resources/js/scripts.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var result = '<c:out value="${result}"/>';
	checkModal(result);
	history.replaceState({},null,null);
	var actionForm = $("#actionForm");
	function checkModal(result){
		if(result===''||history.state){
			return;
		}
		if(parseInt(result)>0){
			$(".modal-body").html("게시글 "+parseInt(result)+" 번이 등록되었습니다.");
		}
		$("#myModal").modal("show");
	}
	$(".move").on("click", function(e){
		e.preventDefault();
		actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
		actionForm.attr("action","/board/read");
		actionForm.submit();
	});
	$("#btnWriteForm").on("click",function(){
		self.location = "/board/register";
	});
	$(".paginate_button a").on("click",function(e){
		e.preventDefault();
		console.log("click");
		actionForm.find('input[name="pageNum"]').val($(this).attr("href"));
		actionForm.submit();
	});
	
	$(".btn btn-default").on("click",function(e){
		$('#modal').modal("hide");
	});
	var searchForm=$("#searchForm");
	$("#searchForm button").on("click", function(e){
		if(!searchForm.find("option:selected").val()){
			alert("검색종류를 선택하세요");
			return false;
		}
		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하세요");
			return false;
		}
		searchForm.find("input[name='pageNum']").val("1");
		e.preventDefault();
		searchForm.submit();
	});
});
</script>
</body>
</html>
