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

	<title>BioTracker | Admin Dashboard</title>

	<link href="../../examples/css/app.css" rel="stylesheet">
</head>

<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar">
			<div class="sidebar-content js-simplebar">
				<a class="sidebar-brand" href="admin_panel.html">
          <span class="align-middle">BIOTRACKER</span>
        </a>

				<ul class="sidebar-nav">
					<li class="sidebar-header">
						Pages
					</li>

					<li class="sidebar-item">
						<a class="sidebar-link" href="admin_panel.html">
              <i class="align-middle" data-feather="home"></i> <span class="align-middle">Dashboard</span>
            </a>
					</li>


					<li class="sidebar-item active">
						<a class="sidebar-link" href="sellers.php">
              <i class="align-middle" data-feather="users"></i> <span class="align-middle">Sellers</span>
            </a>
					</li>

					<li class="sidebar-item">
						<a class="sidebar-link" href="#">
              <i class="align-middle" data-feather="shopping-bag"></i> <span class="align-middle">Products</span>
            </a>
					</li>

					<li class="sidebar-item">
						<a class="sidebar-link" href="#">
              <i class="align-middle" data-feather="file-text"></i> <span class="align-middle">Reports</span>
            </a>
					</li>

				</ul>

			</div>
		</nav>

		<div class="main">
			<nav class="navbar navbar-expand navbar-light navbar-bg">
				<a class="sidebar-toggle d-flex">
          <i class="hamburger align-self-center"></i>
        </a>

				<div class="navbar-collapse collapse">
					<ul class="navbar-nav navbar-align">
						<li class="nav-item dropdown">
							<a class="nav-icon dropdown-toggle d-inline-block d-sm-none" href="#" data-toggle="dropdown">
                <i class="align-middle" data-feather="settings"></i>
              </a>

							<a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#" data-toggle="dropdown">
                <img src="../../examples/img/avatars/avatar.jpg" class="avatar img-fluid rounded mr-1" alt="Admin" /> <span class="text-dark">Admin</span>
              </a>
							<div class="dropdown-menu dropdown-menu-right">
								<a class="dropdown-item" href="#"><i class="align-middle mr-1" data-feather="user"></i> Profile</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="../../index.php"><i class="align-middle mr-1" data-feather="log-out"></i>Log out</a>
							</div>
						</li>
					</ul>
				</div>
			</nav>

			<main class="content">
				<div class="container-fluid p-0">

					<div class="row mb-2 mb-xl-3">
						<div class="col-auto d-none d-sm-block">
							<h3><strong>Sellers</strong></h3>
						</div>

					</div>
                    <div class="row">
                        <div class="col-12 col-xl-12">
                            <div class="card">
                                <form method="post" action="">
                                    <div class="card-body">
                                        <?php
                                        include_once '../../DbConnect.php';

                                        $sel_seller = "select * from seller where seller_id=".htmlspecialchars($_GET['seller_id']);
                                        $res_seller = $conn->query($sel_seller);
                                        $row_seller = $res_seller->fetch_array();

                                        ?>
                                        <div class="form-group">
                                            <label class="form-label">Seller Name</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;
                                            <?php
                                            echo "<label class='form-label'>$row_seller[1]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Address</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <?php
                                            echo "<label class='form-label'>$row_seller[3], $row_seller[2], $row_seller[4]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Mobile Number</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <?php
                                            echo "<label class='form-label'>$row_seller[5]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Email Address</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <?php
                                            echo "<label class='form-label'>$row_seller[6]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">UPI ID</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <?php
                                            echo "<label class='form-label'>$row_seller[7]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">GSTIN</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <?php
                                            echo "<label class='form-label'>$row_seller[8]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Status</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <?php
                                            if ($row_seller[9]==0)
                                            {
                                                echo "<label class='form-label'>In review</label>";
                                            }
                                            if ($row_seller[9]==1)
                                            {
                                                echo "<label class='form-label'>Approved</label>";
                                            }
                                            $seller_id = "".htmlspecialchars($_GET['seller_id']);
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Select Action</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <select class='form-control-sm col-sm-3' name="seller_status" id="seller_status">
                                                <option value="1">Approve</option>
                                                <option value="0">Cancel</option>
                                            </select>
                                        </div>
                                        <button type='submit' class='btn btn-primary' name="update_status" id="update_status">Update</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

				</div>
			</main>
		</div>
	</div>

	<script src="js/vendor.js"></script>
	<script src="../../examples/js/app.js"></script>

</body>

</html>

<?php

if (isset($_POST['update_status']))
{
    $seller_status = $_POST['seller_status'];
    $seller_update = "update seller set seller_status='$seller_status' where seller_id='$seller_id'";
    $res_seller_update = $conn->query($seller_update);
    if ($res_seller_update)
    {
        echo "<script>alert('Updated status')</script>";
        echo "<script>window.location='sellers.php'</script>";
    }
}