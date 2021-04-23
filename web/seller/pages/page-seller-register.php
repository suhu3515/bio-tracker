<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Responsive Web UI Kit &amp; Dashboard Template based on Bootstrap">
	<meta name="author" content="AdminKit">
	<meta name="keywords" content="adminkit, bootstrap, web ui kit, dashboard template, admin template">

	<link rel="shortcut icon" href="../../examples/img/icons/icon-48x48.png" />

	<title>Seller Registration | Bio Tracker</title>

	<link href="../../examples/css/app.css" rel="stylesheet">

	<script type="text/javascript">

		function validation()
		{
			let name = document.forms["seller_reg_form"]["sellername"];
			let place = document.forms["seller_reg_form"]["sellerplace"];
			let address = document.forms["seller_reg_form"]["selleraddr"];
			let dst = document.forms["seller_reg_form"]["sellerdst"];
			let mob = document.forms["seller_reg_form"]["sellerphone"];
			let mail = document.forms["seller_reg_form"]["sellermail"];
			let upi = document.forms["seller_reg_form"]["sellerupi"];
			let gstin = document.forms["seller_reg_form"]["sellergstin"];
			let pass = document.forms["seller_reg_form"]["sellerpass"];
			let cpass = document.forms["seller_reg_form"]["sellercpass"];
			let mail_format = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
			let letters = /^[a-z][a-z\s]*$/;


			if (mob.value.length!== 10)
			{
				alert('Mobile number should be 10 digits');
				mob.focus();
				return false;
			}

			if (mail.value.match(mail_format)) {
				return true;
			}
			else
			{
				alert('Please enter correct Email format');
				mail.focus();
				return false;
			}

			if (dst.value == "nothing")
			{
				alert('Please select the district');
				dst.focus();
				return false;
			}

			if (pass.value.length < 6)
			{
				alert('Password should be greater than 6');
				pass.focus();
				return false;
			}

			if (pass.value != cpass.value)
			{
				alert('Passwords donot match');
				cpass.focus();
				return false;
			}

			return true;
		}

	</script>

</head>

<body>
	<div class="wrapper">

		<div class="main">
			<nav class="navbar navbar-expand navbar-light navbar-bg">
				<a class="sidebar-toggle d-flex">
					<h1>BIOTRACKER</h1>
        </a>

			</nav>

			<main class="content">
				<div class="container-fluid p-0">

					<h1 class="h3 mb-12">Seller Registration</h1>

					<form method="post" name="seller_reg_form" action="" onsubmit="return validation()">
						<div class="row">
							<div class="col-6">
								<div class="card">
									<div class="card-body">
										<label for="sellername">Seller Name <span>*</span></label>
										<input type="text" class="form-control" placeholder="Name" name="sellername" id="sellername" required>
									</div>

									<div class="card-body">
										<label for="selleraddr">Seller Address <span>*</span></label>
										<input type="text" class="form-control" placeholder="Address" name="selleraddr" id="selleraddr" required>
									</div>

									<div class="card-body">
										<label for="sellerphone">Seller Mobile <span>*</span></label>
										<input type="number" class="form-control" placeholder="Mobile" maxlength="10" name="sellerphone" id="sellerphone" required>
									</div>

									<div class="card-body">
										<label for="sellerupi">Seller UPI <span>*</span></label>
										<input type="text" class="form-control" placeholder="Upi Id or number" name="sellerupi" id="sellerupi" required>
									</div>

									<div class="card-body">
										<label for="sellergstin">Seller GSTIN <span>*</span></label>
										<input type="text" class="form-control" placeholder="GSTIN" name="sellergstin" id="sellergstin" required>
									</div>


								</div>
							</div>

							<div class="col-6">
								<div class="card">
									<div class="card-body">
										<label for="sellerplace">Seller Place <span>*</span></label>
										<input type="text" class="form-control" placeholder="Place" name="sellerplace" id="sellerplace" required>
									</div>

									<div class="card-body">
										<label for="sellerdst">Seller District <span>*</span></label>
										<select class="form-control" name="sellerdst" id="sellerdst" required>
											<option value="nothing" selected>Select District</option>
											<option value="Alappuzha">Alappuzha</option>
											<option value="Ernamkulam">Ernamkulam</option>
											<option value="Idukki">Idukki</option>
											<option value="Kannur">Kannur</option>
											<option value="Kasargod">Kasargod</option>
											<option value="Kollam">Kollam</option>
											<option value="Kottayam">Kottayam</option>
											<option value="Kozhikode">Kozhikode</option>
											<option value="Malappuram">Malappuram</option>
											<option value="Palakkad">Palakkad</option>
											<option value="Pathanamthitta">Pathanamthitta</option>
											<option value="Thiruvananthapuram">Thiruvananthapuram</option>
											<option value="Thrissur">Thrissur</option>
											<option value="Wayanad">Wayanad</option>
										</select>
									</div>

									<div class="card-body">
										<label for="sellermail">Seller Mail <span>*</span></label>
										<input type="email" class="form-control" placeholder="Email Address" name="sellermail" id="sellermail" required>
									</div>

									<div class="card-body">
										<label for="sellerpass">Password <span>*</span></label>
										<input type="password" class="form-control" placeholder="Password" name="sellerpass" id="sellerpass" required>
									</div>

									<div class="card-body">
										<label for="sellercpass">Seller Mail <span>*</span></label>
										<input type="password" class="form-control  mb-3" placeholder="Confirm Password" name="sellercpass" id="sellercpass" required>
										<button class="btn btn-primary" type="submit" name="add_seller" id="add_seller">REGISTER</button>
									</div>

								</div>
							</div>
						</div>

					</form>

				</div>
			</main>

		</div>
	</div>

	<script src="js/vendor.js"></script>
	<script src="../../examples/js/app.js"></script>

</body>

</html>

<?php

$server_name = "localhost";
$user_name = "root";
$password = "";
$database = "biotracker";

$conn = new mysqli($server_name, $user_name, $password, $database);

if (isset($_POST['add_seller']))
{
    $seller_name = $_POST['sellername'];
    $seller_addr = $_POST['selleraddr'];
    $seller_phone = $_POST['sellerphone'];
    $seller_upi = $_POST['sellerupi'];
    $seller_gstin = $_POST['sellergstin'];
    $seller_place = $_POST['sellerplace'];
    $seller_dst = $_POST['sellerdst'];
    $seller_mail = $_POST['sellermail'];
    $pass = $_POST['sellerpass'];
    $cpass = $_POST['sellercpass'];
    $log_role = "SELLER";
    $check_phone = "select * from login where mobile='$seller_phone'";
    $phone_res = mysqli_query($conn, $check_phone);
    if ($phone_res->num_rows > 0)
    {
    echo "<script>alert('An account with the same mobile number is found.' +
		'try with another mobile number.')</script>";
    }
    else if ($seller_dst=="nothing")
    {
        echo "<script>alert('Please select a district...')</script>";
    }
    else if($pass != $cpass)
    {
        echo "<script>alert('Please confirm your password...')</script>";
        echo  "<script>document.getElementById('pass').value=''</script>";
    }
    else
    {
        $choreo_pass = $pass;
        $ins_seller =  "INSERT INTO seller (seller_name,seller_place,seller_addr,seller_dst,seller_phone,seller_mail,seller_upi_id,seller_gstin) values ('$seller_name','$seller_place','$seller_addr','$seller_dst','$seller_phone','$seller_mail','$seller_upi','$seller_gstin')";
        $ins_login = "INSERT INTO login (mobile,password,role) values ('$seller_phone','$pass','$log_role')";
        $reg = mysqli_query($conn, $ins_seller);
        $log = mysqli_query($conn, $ins_login);

        if($log == true && $reg == true)
        {
            echo "<script>alert('Seller registered Successfully. Review in progress')</script>";
            echo "<script>window.location='../../index.php'</script>";
        }
        else
        {
            echo "<script>alert('Error in registration')</script>";
        }
    }
}
