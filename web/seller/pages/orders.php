<?php

include_once  '../../DbConnect.php';
session_start();
$seller_id = $_SESSION["seller_id"];

?>
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

	<title>BioTracker | Seller Dashboard</title>

	<link href="../../examples/css/app.css" rel="stylesheet">
</head>

<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar">
			<div class="sidebar-content js-simplebar">
				<a class="sidebar-brand" href="seller_panel.html">
          <span class="align-middle">BIOTRACKER</span>
        </a>

				<ul class="sidebar-nav">
					<li class="sidebar-header">
						Pages
					</li>

					<li class="sidebar-item">
						<a class="sidebar-link" href="seller_panel.html">
              <i class="align-middle" data-feather="home"></i> <span class="align-middle">Dashboard</span>
            </a>
					</li>


					<li class="sidebar-item">
						<a class="sidebar-link" href="products.php">
              <i class="align-middle" data-feather="shopping-bag"></i> <span class="align-middle">Products</span>
            </a>
					</li>

					<li class="sidebar-item active">
						<a class="sidebar-link" href="orders.php">
              <i class="align-middle" data-feather="shopping-cart"></i> <span class="align-middle">Orders</span>
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
                <img src="../../examples/img/avatars/s_icon.jpg" class="avatar img-fluid rounded mr-1" alt="Seller" /> <span class="text-dark">Seller</span>
              </a>
							<div class="dropdown-menu dropdown-menu-right">
								<a class="dropdown-item" href="change_pass.php"><i class="align-middle mr-1" data-feather="user"></i> Change Password</a>
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
                            <h3><strong>Orders</strong></h3>
                        </div>

                    </div>
                    <div class="row">

                        <div class="col-12 col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <form method="post" action="">
                                        <select class="form-group col-md-3" name="select_status" id="select_status">
                                            <option value="nothing" selected>Select status</option>
                                            <option value="0">Rejected</option>
                                            <option value="1">Confirmed</option>
                                            <option value="2">Packed</option>
                                            <option value="3">Dispatched</option>
                                            <option value="4">Completed</option>
                                        </select>
                                        &nbsp;&nbsp;&nbsp;
                                        <input type="text" class="form-group col-md-4" id="user_name" name="user_name" placeholder="User Name">
                                        &nbsp;&nbsp;&nbsp;
                                        <button type="submit" name="search_orders" id="search_orders" class="btn btn-primary col-md-2">Search</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-xl-12">
                            <div class="card">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>User Name</th>
                                        <th>Product</th>
                                        <th>Quantity</th>
                                        <th>Amount</th>
                                        <th class="d-none d-md-table-cell" >Payment Status</th>
                                        <th>Status</th>
                                        <th>Update</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <?php

                                    if (isset($_POST['search_orders']))
                                    {
                                        $status = $_POST['select_status'];
                                        $user = $_POST['user_name'];
                                        if ($status=="nothing")
                                        {
                                            echo "<script>alert('Please select status')</script>";
                                        }
                                        else
                                        {
                                            if ($user=="")
                                            {
                                                $search = $user."%";
                                            }
                                            else
                                            {
                                                $search = "%".$user."%";
                                            }
                                            $order_sel = "select * from orders where product_id in (select product_id from marketplace where seller_id='$seller_id' and product_status='1') and order_status='$status' and user_id in (select user_id from users where user_name LIKE '$search')";
                                            $order_res = $conn->query($order_sel);
                                            while ($order_row = $order_res->fetch_array())
                                            {
                                                echo "<tr>";
                                                $res_user = $conn->query("select user_name from users where user_id='$order_row[3]'");
                                                while ($row_user = $res_user->fetch_array())
                                                {
                                                    echo "<td>$row_user[0]</td>";
                                                }
                                                $res_prod = $conn->query("select product_name from marketplace where product_id='$order_row[1]'");
                                                while ($row_prod = $res_prod->fetch_array())
                                                {
                                                    echo "<td>$row_prod[0]</td>";
                                                }
                                                echo "<td>$order_row[2]</td>";
                                                echo "<td>INR $order_row[4]</td>";
                                                if ($order_row[9] == 0)
                                                {
                                                    echo "<td style='color: red'>Not paid</td>";
                                                }
                                                if ($order_row[9] == 1)
                                                {
                                                    echo "<td style='color: green'>Paid</td>";
                                                }
                                                if ($order_row[10] == 0)
                                                {
                                                    echo "<td>Rejected</td>";
                                                }
                                                if ($order_row[10] == 1)
                                                {
                                                    echo "<td>Confirmed</td>";
                                                }
                                                if ($order_row[10] == 2)
                                                {
                                                    echo "<td>Packed</td>";
                                                }
                                                if ($order_row[10] == 3)
                                                {
                                                    echo "<td>Dispatched</td>";
                                                }
                                                if ($order_row[10] == 4)
                                                {
                                                    echo "<td>Completed</td>";
                                                }
                                                echo "<td><a href='order_details.php?order_id=$order_row[0]'><button class='btn btn-primary'>Update</button></a></td>";
                                            }
                                        }
                                    }
                                    ?>
                                    </tbody>
                                </table>
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