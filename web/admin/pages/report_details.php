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

                <li class="sidebar-item">
                    <a class="sidebar-link" href="products.php">
                        <i class="align-middle" data-feather="shopping-bag"></i> <span class="align-middle">Products</span>
                    </a>
                </li>

                <li class="sidebar-item active">
                    <a class="sidebar-link" href="reports.php">
                        <i class="align-middle" data-feather="file-text"></i> <span class="align-middle">Reports</span>
                    </a>
                </li>

                <li class="sidebar-item">
                    <a class="sidebar-link" href="">
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
                        <h3><strong>Report</strong></h3>
                    </div>

                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <?php
                                include_once '../../DbConnect.php';

                                $report = $_GET['report_id'];
                                $sel_report = "select * from reports where report_id='$report'";
                                $res_report = $conn->query($sel_report);
                                $row_report = $res_report->fetch_array();

                                $sel_post = "select caption,post_image from community_post where post_id='$row_report[3]'";
                                $res_post = $conn->query($sel_post);
                                $row_post = $res_post->fetch_array();

                                ?>
                                <div class="form-group">
                                    <label class="form-group"><strong>Report Reason</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$row_report[2]</label>";
                                    ?>
                                </div>
                                <div class="form-group">
                                    <label class="form-group"><strong>Post Caption</strong></label><br>
                                    <?php
                                    echo "<label class='form-group'>$row_post[0]</label>";
                                    ?>
                                </div>
                                <?php
                                if ($row_post[1]!=null)
                                {
                                    echo "<div class='form-group'>";
                                    echo "<label class='form-group'><strong>Post Image</strong></label><br><br>";
                                    $file_loc = "../../".$row_post[1];
                                    echo "<img src='$file_loc' width='20%' height='30%'>";
                                }
                                ?>
                                <div class="form-group">
                                    <br><br><label class="form-group"><strong>Update Post</strong></label><br><br>
                                    <form action="" method="post">
                                        <button class="btn btn-danger" type="submit" name="del_post" id="del_post">Delete Post</button>
                                        <a href="reports.php"><button type="button" class="btn btn-primary">Cancel</button></a>
                                    </form>
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

if (isset($_POST['del_post']))
{
    $post_status = $conn->query("update community_post set status='0' where post_id='$row_report[3]'");
    $report_status = $conn->query("update reports set report_status='0' where report_id='$report'");
    if ($post_status && $report_status)
    {
        echo "<script>alert('Updated status')</script>";
        echo "<script>window.location='reports.php'</script>";
    }
}