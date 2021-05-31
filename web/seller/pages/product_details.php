<?php
include_once '../../DbConnect.php';

session_start();

$seller = $_SESSION['seller_id'];

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
                        </div>

                    </div>
                    <?php

                        $product = $_GET['prod_id'];
                        $sel_prod = "select * from marketplace where product_id='$product'";
                        $res_prod = $conn->query($sel_prod);
                        $row_prod = $res_prod->fetch_array();

                    ?>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                        <div class="form-group>
                                            <label for="prod_name">Name</label><br>
                                        <?php
                                            echo "<label class='form-group'>$row_prod[1]</label>";
                                        ?>
                                        </div>
                                        <div class="form-group">
                                            <label for="prod_price">Price</label><br>
                                            <?php
                                            echo "<label class='form-group'>$row_prod[2]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label for="prod_qty">Quantity</label>
                                            <?php
                                            echo "<label class='form-group'>$row_prod[3]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <label for="prod_desc">Description</label><br>
                                            <?php
                                            echo "<label class='form-group'>$row_prod[4]</label>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <a href="products.php"><button class="btn btn-primary">Go back <span class="fa fa-arrow-left"></span></button></a>
                                        </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <h3><strong>Update Stock</strong></h3>
                                    <form method="post" action="">
                                        <div class="form-group">
                                            <br><label for="upd_qty">Quantity</label><br>
                                            <input name="upd_qty" id="upd_qty" type="number" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="upd_price">Price</label><br>
                                            <?php
                                                echo "<input name='upd_price' id='upd_price' type='number' class='form-control' value='$row_prod[2]' required>";
                                            ?>
                                        </div>
                                        <div class="form-group">
                                            <button name="upd_stock" id="upd_stock" type="submit" class="btn btn-success">Update Stock</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="prod_img">Product Image</label><br>
                                        <?php
                                        echo "<img src='$row_prod[5]'>";
                                        ?>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="../../examples/js/vendor.js"></script>
    <script src="../../examples/js/app.js"></script>
    </body>
    </html>

<?php
if (isset($_POST['upd_stock']))
{

    $price = $_POST['upd_price'];
    $qty = $_POST['upd_qty'];
    $total = $row_prod[3] + $qty;

    if ($row_prod[7]==0)
    {
        echo "<script>alert('Cannot update stock when product is in review.')</script>";
        echo "<script>window.location='products.php'</script>";
    }
    else
    {
        $res_upd = $conn->query("update marketplace set product_qty='$total', product_price='$price' where product_id='$row_prod[0]'");
        if ($res_upd)
        {
            //echo "<script>alert('$total')</script>";
            echo "<script>alert('Updated stock successfully...')</script>";
            echo "<script>window.location='products.php'</script>";
        }
    }

}