<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main">
			<jsp:include page="header.jsp">
				<jsp:param name="headerVar" value="${param.headerVar}" />
			</jsp:include>
			<div class="content">
				<form class="form-horizontal my-form" action="${param.action}" method="POST">
					<div class="form-group">
						<label for="id">شناسه</label>
						<input type="text" class="form-control" name="id" id="id" placeholder="شناسه">
					</div>

					<div class="form-group">
						<label for="instrument">شرکت</label>
						<input type="text" class="form-control" name="instrument" id="instrument" placeholder="شرکت">
					</div>

					<div class="form-group">
						<label for="price">قیمت</label>
						<div class="input-group" dir="ltl">
							<div class="input-group-addon"> ریال</div>
							<input type="text" class="form-control" name="price" id="price" placeholder="قیمت " dir="rtl">
						</div>
					</div>

					<div class="form-group">
						<label for="quantity">تعداد</label>
						<div class="input-group">
							<input type="number" class="form-control" name="quantity" id="quantity" placeholder="0">
						</div>
					</div>

					<div class="form-group">
						<label>نوع</label>	
						<div class="radio">
							<label><input type="radio" name="type" id="type1" value="GTC" checked></label><label>GTC</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="type" id="type2" value="IOC"></label><label>IOC</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="type" id="type3" value="MPO"></label><label>MPO</label>
						</div>
					</div>

					<div class="form-group">
						<button type="submit" class="btn btn-default submit-btn">${param.submitVar}</button>
					</div>
				</form>
			</div>
		</div>
