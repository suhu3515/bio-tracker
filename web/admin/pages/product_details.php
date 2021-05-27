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


                <li class="sidebar-item">
                    <a class="sidebar-link" href="sellers.php">
                        <i class="align-middle" data-feather="users"></i> <span class="align-middle">Sellers</span>
                    </a>
                </li>

                <li class="sidebar-item active">
                    <a class="sidebar-link" href="products.php">
                        <i class="align-middle" data-feather="shopping-bag"></i> <span class="align-middle">Products</span>
                    </a>
                </li>

                <li class="sidebar-item">
                    <a class="sidebar-link" href="reports.php">
                        <i class="align-middle" data-feather="file-text"></i> <span class="align-middle">Reports</span>
                    </a>
                </li>

                <li class="sidebar-item">
                    <a class="sidebar-link" href="tutorials.php">
                        <i class="align-middle" data-feather="film"></i> <span class="align-middle">Tutorials</span>
                    </a>
                </li>

                <li class="sidebar-item">
                    <a class="sidebar-link" href="instructions.php">
                        <i class="align-middle" data-feather="info"></i> <span class="align-middle">Instructions</span>
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
                        <h3><strong>Products</strong></h3>
                    </div>

                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body">
                                <?php
                                include_once '../../DbConnect.php';

                                $product = $_GET['prod_id'];
                                $sel_prod = "select * from marketplace where product_id='$product'";
                                $res_prod = $conn->query($sel_prod);
                                $row_prod = $res_prod->fetch_array();

                                $sel_seller = "select * from seller where seller_id='$row_prod[6]'";
                                $res_seller = $conn->query($sel_seller);
                                $row_seller = $res_seller->fetch_array();

                                ?>
                                <div class="form-group">
                                    <label class="form-group"><strong>Name</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$row_prod[1]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label class="form-group"><strong>Price</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$row_prod[2]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label class="form-group"><strong>Quantity</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$row_prod[3]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label class="form-group"><strong>Description</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$row_prod[4]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label class="form-group"><strong>Product Image</strong></label><br>
                                    <?php
                                    $file_loc = "../../seller/pages/".$row_prod[5];
                                    echo "<img src='$file_loc'>";
                                    ?>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <form method="post" action="">
                                <div class="card-body">
                                    <h3><strong>Seller</strong></h3>
                                    <div class="form-group">
                                        <label class="form-group"><strong>Seller Name</strong></label><br>
                                        <?php
                                        echo "<label class='form-group'>$row_seller[1]</label>";
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-group"><strong>Seller Address</strong></label><br>
                                        <?php
                                        echo "<label class='form-group'>$row_seller[3]<br>$row_seller[2]<br>$row_seller[4]</label>";
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-group"><strong>Seller Mobile</strong></label><br>
                                        <?php
                                        echo "<label class='form-group'>$row_seller[5]</label>";
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-group"><strong>Seller Email</strong></label>
                                        <?php
                                        echo "<label class='form-group'>$row_seller[6]</label>";
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-group"><strong>Select Action</strong></label>
                                        <select class='form-control col-md-6' name="product_status" id="product_status">
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
    $product_status = $_POST['product_status'];
    $product_update = "update marketplace set product_status='$product_status' where product_id='$row_prod[0]'";
    $res_product_update = $conn->query($product_update);
    if ($res_product_update)
    {
        echo "<script>alert('Updated status')</script>";
        echo "<script>window.location='products.php'</script>";
    }
}