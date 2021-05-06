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
                            <h3><strong>Orders</strong></h3>
                        </div>

                    </div>
                    <?php

                    $order = $_GET['order_id'];
                    $order_res = $conn->query("select * from orders where order_id='$order'");
                    $order_row = $order_res->fetch_array();

                    $product_sel = $conn->query("select product_name, product_desc from marketplace where product_id='$order_row[1]'");
                    $product_row = $product_sel->fetch_array();

                    $user_sel = $conn->query("select user_name from users where user_id='$order_row[3]'");
                    $user_row = $user_sel->fetch_array();

                    ?>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body">
                                    <h3><strong>Product details</strong></h3><br>
                                    <div class="form-group">
                                        <label><strong>Product Name</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$product_row[0]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label><strong>Description</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$product_row[1]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label><strong>Order Quantity</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$order_row[2]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label><strong>Order Amount</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$order_row[4]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label><strong>Payment Mode</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$order_row[6]</label>";
                                    ?>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body">
                                <h3><strong>Shipment details</strong></h3>
                                <form method="post" action="">
                                    <div class="form-group">
                                        <br><label><strong>User Name</strong></label><br>
                                        <?php
                                        echo "<label class='form-group'>$user_row[0]</label>";
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label><strong>Shipping Address</strong></label><br>
                                        <?php
                                        echo "<label class='form-group'>$order_row[5]</label>";
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label><strong>Payment Status</strong></label><br>
                                        <?php
                                        if($order_row[8]==0)
                                        {
                                            echo "<label class='form-group'>Not paid</label>";
                                        }
                                        if ($order_row[8]==1)
                                        {
                                            echo "<label class='form-group'>Paid</label>";
                                        }
                                        ?>
                                    </div>
                                    <div class="form-group">
                                        <label><strong>Order Status</strong></label><br>
                                        <?php
                                        if($order_row[9]==0)
                                        {
                                            echo "<label class='form-group'>Rejected</label>";
                                        }
                                        if ($order_row[9]==1)
                                        {
                                            echo "<label class='form-group'>Confirmed</label>";
                                        }
                                        if ($order_row[9]==2)
                                        {
                                            echo "<label class='form-group'>Dispatched</label>";
                                        }
                                        if ($order_row[9]==3)
                                        {
                                            echo "<label class='form-group'>Completed</label>";
                                        }
                                        ?>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body">
                                <h3><strong>Update Order</strong></h3>
                                <form method="post" action="">
                                    <div class="form-group">
                                        <br><label><strong>Status</strong></label><br>
                                        <select class="form-control" name="select_status" id="select_status">
                                            <option value="nothing" selected>Select status</option>
                                            <option value="0">Rejected</option>
                                            <option value="1">Confirmed</option>
                                            <option value="2">Dispatched</option>
                                            <option value="3">Completed</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="estdeliverydate"><strong>Estimated date</strong></label><br>
                                        <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </span>
                                            <input id='estdeliverydate' type='date' class='form-control' name='estdeliverydate' required>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <br><button name="upd_order" id="upd_order" type="submit" class="btn btn-success">Update Order</button>
                                    </div>
                                </form>
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
if (isset($_POST['upd_order']))
{

    $status = $_POST['select_status'];
    $est_date = $_POST['estdeliverydate'];
    $today_date = date("Y-m-d");

    if ($status=="nothing")
    {
        echo "<script>alert('Please select a valid status')</script>";
    }
    else if ($status=="0")
    {
        $res_upd = $conn->query("update orders set order_status='$status', delivery_date=null where order_id='$order'");
        if ($res_upd)
        {
            //echo "<script>alert('$total')</script>";
            echo "<script>alert('Delivery date excluded and updated order')</script>";
            echo "<script>window.location='orders.php'</script>";
        }

    }
    else if ($est_date<$today_date)
    {
        echo "<script>alert('Please select a date from today')</script>";
    }
    else
    {
        $res_upd = $conn->query("update orders set order_status='$status', delivery_date='$est_date' where order_id='$order'");
        if ($res_upd)
        {
            //echo "<script>alert('$total')</script>";
            echo "<script>alert('Updated order successfully...')</script>";
            echo "<script>window.location='orders.php'</script>";
        }
    }

}