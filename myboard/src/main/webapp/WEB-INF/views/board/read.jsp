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
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="/resources/js/reply.js"></script>
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
			<h1 class="fw-bolder">Board Read</h1>
		</div>
	</header>
	<!-- About section-->
	<div class="container px-4">
		<div class="row gx-4 justify-content-center">
			<div class="col-lg-8">
				<h2>게시글 조회</h2>
				<table class="table table-hover">
					<div class="panel-body">
						<div class="form-group">
							<label>번호</label><input class="form-control" name="bno"
								value='<c:out value="${board.bno}" />' readonly="readonly">
						</div>
						<div class="form-group">
							<label>제목</label><input class="form-control" name="title"
								value='<c:out value="${board.title}" />' readonly="readonly">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="3" name='content'
								value='<c:out value="${board.content}" />' readonly="readonly"><c:out
									value="${board.content}" /></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label><input class="form-control" name="writer"
								value='<c:out value="${board.writer}" />' readonly="readonly">
						</div>
						<button data-oper='modify' class="btn btn-default">수정</button>
						<button data-oper='list' class="btn btn-info">목록</button>
						<form id='operForm' action="/board/modify" method="get">
							<input type='hidden' id='bno' name='bno'
								value='<c:out value="${board.bno}"/>'> <input
								type='hidden' id='pageNum' name='pageNum'
								value='<c:out value="${cri.pageNum}"/>'> <input
								type='hidden' id='amount' name='amount'
								value='<c:out value="${cri.amount}"/>'> <input
								type='hidden' id='type' name='type'
								value='<c:out value="${cri.type}"/>'> <input
								type='hidden' id='keyword' name='keyword'
								value='<c:out value="${cri.keyword}"/>'>
						</form>
					</div>
				</table>
				<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-comments fa-fw"></i>Reply
							<button id='addReplyBtn'
								class='btn btn-primary btn-xs pull-right'>New Reply</button>
						</div>
						<div class="panel-body">
							<ul class="chat">
								<li class="left clearfix" data-rno='12'>
							</ul>
						</div>
						<div class="panel-footer">
						
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>Reply</label> <input class="form-control" name="reply"
							value="New Reply!">
					</div>
					<div class="form-group">
						<label>Replyer</label> <input class="form-control" name="replyer"
							value="replyer">
					</div>
					<div class="form-group">
						<label>Reply Date</label> <input class="form-control"
							name='replyDate' value=''>
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
					<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
					<button id="modalRegisterBtn" type="button" class="btn btn-primary">Register</button>
					<button id="modalCloseBtn" type="button" class="btn btn-default"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	</div>
	</div>
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
	$(document).ready(function() {
		var operForm = $("#operForm");
		$("button[data-oper='modify']").on("click", function(e) {
			operForm.attr("action", "/board/modify").submit();
		});
		$("button[data-oper='list']").on("click", function(e) {
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list")
			operForm.submit();
		});
	});
	</script>
<script>
	
	$(document).ready(function(){
		var bnoValue='<c:out value="${board.bno}"/>';
		var replyUL=$(".chat");
		
			showList(1);
			function showList(page){
				console.log("show list "+page);
				replyService.getList({bno:bnoValue,page:page||1},function(replyCnt, list){
					console.log("replyCnt : " +replyCnt);
					console.log("list : " +list);
					console.log(list);
					
					if(page==-1){
						pageNum = Math.ceil(replyCnt/10.0);
						showList(pageNum);
						return;
					}
					
					var str="";
					if(list == null || list.length == 0){
						return;
					}
					for(var i=0,len=list.length || 0; i<len;i++){
						str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
						str +="	<div><div class='header'><strong class=='primary-font'>"+list[i].replyer+"</strong>";
						str +="		<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
						str +="			<p>"+list[i].reply+"</p></div></li>";	
					}
					replyUL.html(str);
					for(var i=0,len=list.length||0;i<len;i++){
						
					}
					replyUL.html(str);
					showReplyPage(replyCnt);
				});
			}
		var modal=$('.modal');
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		
		$("#addReplyBtn").on("click", function(e){
			modal.find("input").val("");
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id != 'modalCloseBtn']").hide();
			
			modalRegisterBtn.show();
			$('.modal').modal("show");
		});
		modalRegisterBtn.on("click",function(e){
			var reply={
					reply:modalInputReply.val(),
					replyer:modalInputReplyer.val(),
					bno:bnoValue
			};
			replyService.add(reply, function(result){
				alert(result);
				modal.find("input").val("");
				modal.modal("hide");
				showList(-1);
			});
		});
		
		$(".chat").on("click","li",function(e){
			var rno = $(this).data("rno");
			replyService.get(rno,function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
				modal.data("rno",reply.rno);
				modal.find("button[id != 'modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				$(".modal").modal("show");
			});
			
			modalModBtn.on("click", function(e){
				var reply = {rno:modal.data("rno"), reply:modalInputReply.val()};
				
				replyService.update(reply, function(result){
					alert(result);
					modal.modal("hide");
					showList(pageNum);
				});
			});
			
			modalRemoveBtn.on("click", function(e){
				var rno=modal.data("rno");
				replyService.remove(rno,function(result){
					alert("result");
					modal.modal("hide");
					showList(pageNum);
				});
			});
		});
		var pageNum=1;
		var replyPageFooter=$(".panel-footer");
		
		function showReplyPage(replyCnt){
			var endNum = Math.ceil(pageNum/10.0)*10;
			var startNum = endNum -9;
			
			var prev = startNum != 1;
			var next=false;
			
			if(endNum *10 >=replyCnt){
				endNum = Math.ceil(replyCnt/10.0);
			}
			if(endNum * 10 <replyCnt){
				next=true;
			}
			
			var str = "<ul class='pagination pull-right'>";
			
			if(prev){
				str+="<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Privious</a></li>";
			}
			for(var i = startNum;i<=endNum;i++){
				var active = pageNum == i ?"active":"";
				str+="<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
			}
			
			if(next){
				str+="<li class='page-item'><a class='page-link' href='"+(endNum +1)+"'>Next</a><li>";
			}
			str+="</ul></div>";
			console.log(str);
			replyPageFooter.html(str);
		}
		replyPageFooter.on("click","li a", function(e){
			e.preventDefault();
			
			var targetPageNum = $(this).attr("href");
			
			console.log("targetPageNum : " + targetPageNum);
			pageNum=targetPageNum;
			showList(pageNum);
		});
	});
</script>
</body>
</html>
