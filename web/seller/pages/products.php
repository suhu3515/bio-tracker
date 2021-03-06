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
				<a class="sidebar-brand" href="seller_panel.php">
          <span class="align-middle">BIOTRACKER</span>
        </a>

				<ul class="sidebar-nav">
					<li class="sidebar-header">
						Pages
					</li>

					<li class="sidebar-item">
						<a class="sidebar-link" href="seller_panel.php">
              <i class="align-middle" data-feather="home"></i> <span class="align-middle">Dashboard</span>
            </a>
					</li>


					<li class="sidebar-item active">
						<a class="sidebar-link" href="products.php">
              <i class="align-middle" data-feather="shopping-bag"></i> <span class="align-middle">Products</span>
            </a>
					</li>

					<li class="sidebar-item">
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
                            <h3><strong>Products</strong></h3>
                            <a href="products_add.php"><button class="btn btn-primary">Add  <span class="fa fa-plus"></span></button></a>
                        </div>

                    </div>
                    <div class="row">

                        <div class="col-12 col-xl-12">
                            <div class="card">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th style="width:20%;">Name</th>
                                        <th style="width:30%">Description</th>
                                        <th style="width:15%">Price</th>
                                        <th class="d-none d-md-table-cell" style="width:10%">Quantity</th>
                                        <th>Status</th>
                                        <th style="width=10%">Details</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <?php

                                    $product_sel = "select product_id,product_name,product_price,product_qty,product_desc,product_status from marketplace where seller_id='$seller_id'";
                                    $product_res = $conn->query($product_sel);
                                    while ($product_row = $product_res->fetch_array())
                                    {
                                        echo "<tr>";
                                        echo "<td>$product_row[1]</td>";
                                        echo "<td>$product_row[4]</td>";
                                        echo "<td>$product_row[2]</td>";
                                        echo "<td>$product_row[3]</td>";
                                        if ($product_row[5] == 0)
                                        {
                                            echo "<td style='color: red'>In review</td>";
                                        }

                                        if ($product_row[5] == 1)
                                        {
                                            echo "<td style='color: green'>Approved</td>";
                                        }
                                        echo "<td><a href='product_details.php?prod_id=$product_row[0]'><button class='btn btn-primary'>Details</button></a></td>";
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