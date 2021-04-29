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

    <script src="js/vendor.js"></script>
    <script src="../../examples/js/app.js"></script>
    </body>
    </html>

<?php
if (isset($_POST['add_products']))
{
    $maxsize = 5242880; //5MB

    $prod_name = $_POST['prod_name'];
    $price = $_POST['prod_price'];
    $qty = $_POST['prod_qty'];
    $desc = $_POST['prod_desc'];

    $name = $_FILES['file']['name'];
    $target_dir = "images/";
    $target_file = $target_dir . basename($_FILES["file"]["name"]);

    $imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

    $extensions_arr = array("jpg","jpeg","png","gif");

    if (in_array($imageFileType, $extensions_arr))
    {
        if (($_FILES['file']['size'] >= $maxsize) || ($_FILES["file"]["size"]==0))
        {
            echo "<script>alert('File too large. File must be less than 5MB.')</script>";
        }
        else
        {
            if (move_uploaded_file($_FILES['file']['tmp_name'],$target_file))
            {
                $product_insert = "insert into marketplace(product_name, product_price, product_qty, product_desc, product_img, seller_id) values ('$prod_name','$price','$qty','$desc','$target_file','$seller')";
                $product_res = $conn->query($product_insert);
                if ($product_res)
                {
                    echo "<script>alert('Product added successfully. Item in review...')</script>";
                    echo "<script>window.location='products.php'</script>";
                }
            }
        }
    }
    else
    {
        echo "<script>alert('Invalid file extension.')</script>";
    }
}