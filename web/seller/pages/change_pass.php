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
                        <h3><strong>Change Password</strong></h3>
                    </div>

                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <form action="" method="post">
                                    <div class="form-group col-md-8">
                                        <label for="currPass">Current Password</label>
                                        <input type="password" class="form-control" id="currPass" name="currPass" placeholder="Current Password" required><br>
                                    </div>
                                    <div class="form-group col-md-8">
                                        <label for="newPass">New Password</label>
                                        <input type="password" class="form-control" id="newPass" name="newPass" placeholder="New Password" required><br>
                                    </div>
                                    <div class="form-group col-md-8">
                                        <label for="confirmPass">Confirm Password</label>
                                        <input type="password" class="form-control"  id="confirmPass" name="confirmPass" placeholder="Confirm Password" required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <br><button type="submit" name="change_pass" id="change_pass" class="btn btn-primary">Change Password</button>
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

<script src="js/vendor.js"></script>
<script src="../../examples/js/app.js"></script>
</body>
</html>

<?php

include_once '../../DbConnect.php';

if (isset($_POST['change_pass']))
{
    $current_pass = $_POST['currPass'];
    $new_pass = $_POST['newPass'];
    $confirm_pass = $_POST['confirmPass'];

    if ($new_pass!==$confirm_pass)
    {
        echo "<script>alert('new password and confirm password not matching')</script>";
    }
    else
    {
        $res_select = $conn->query("select count(*) from login where password='$current_pass' and role='SELLER' and mobile=(select seller_phone from seller where seller_id='$seller')");
        while ($row_select = $res_select->fetch_array())
        {
            if ($row_select[0]>0)
            {
                $res_change = $conn->query("update login set password='$confirm_pass' where mobile=(select seller_phone from seller where seller_id='$seller') and password='$current_pass' and role='SELLER'");
                if ($res_change)
                {
                    echo "<script>alert('Password changed successfully...')</script>";
                    echo "<script>window.location='../../index.php'</script>";
                }
            }
            else
            {
                echo "<script>alert('Please enter correct password')</script>";
            }
        }
    }
}