<!DOCTYPE html>

<html>
<head>
	<title>Buy Form</title>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/form.css">
	<link rel="stylesheet" type="text/css" href="css/index.css">
</head>

<body>
	<div class="container">

		<jsp:include page="nav.jsp" />
		<div class=main>
			<jsp:include page="header.jsp">
				<jsp:param name="headerVar" value="اعتبار دهی" />
			</jsp:include>
			<div class="content">
				<form class="form-horizontal my-form" action="deposit-user" method="POST">
					<div class="form-group">
						<label for="id">شناسه</label>
						<input type="text" class="form-control" name="id" id="id" placeholder="شناسه">
					</div>
					<div class="form-group">
						<label for="id">مقدار</label>
						<input type="text" class="form-control" name="amount" id="amount" placeholder="مقدار">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-default submit-btn">ارسال</button>
					</div>
				</form>
			</div>
		</div>

	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>