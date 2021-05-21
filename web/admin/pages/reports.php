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
                            <h3><strong>Reports</strong></h3>
                        </div>

                    </div>
                    <div class="row">

                        <div class="col-12 col-md-12">
                            <div class="card">
                                <div class="card-body">
                                    <form method="post" action="">
                                        <select class="form-group col-md-3" name="select_status" id="select_status">
                                            <option value="nothing" selected>Select status</option>
                                            <option value="0">Resolved</option>
                                            <option value="1">Active</option>
                                        </select>
                                        &nbsp;&nbsp;&nbsp;
                                        <button type="submit" name="search_reports" id="search_reports" class="btn btn-primary col-md-2">Search</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-xl-12">
                            <div class="card">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th style="width:20%;">Reported User</th>
                                        <th style="width:30%">Report Reason</th>
                                        <th style="width:10%">Post ID</th>
                                        <th style="width:15%">Post Status</th>
                                        <th class="d-none d-md-table-cell" style="width:20%">Report Status</th>
                                        <th>Details</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <?php

                                    include_once '../../DbConnect.php';

                                    if (isset($_POST['search_reports']))
                                    {
                                        $status = $_POST['select_status'];
                                        if ($status=="nothing")
                                        {
                                            echo "<script>alert('Please select a status')</script>";
                                        }
                                        else
                                        {
                                            $reports_sel = "select * from reports where report_status='$status'";
                                            $reports_res = $conn->query($reports_sel);
                                            while ($reports_row = $reports_res->fetch_array())
                                            {
                                                echo "<tr>";
                                                $row_user = mysqli_fetch_array($conn->query("select user_name from users where user_id='$reports_row[1]'"));
                                                echo "<td>$row_user[0]</td>";
                                                echo "<td>$reports_row[2]</td>";
                                                echo "<td>$reports_row[3]</td>";
                                                $row_posts = mysqli_fetch_array($conn->query("select status from community_post where post_id='$reports_row[3]'"));
                                                if ($row_posts[0]==0)
                                                {
                                                    echo "<td style='color: red'>Post Deleted</td>";
                                                }
                                                if ($row_posts[0]==1)
                                                {
                                                    echo "<td style='color: green'>Post Active</td>";
                                                }
                                                if ($reports_row[4] == 0)
                                                {
                                                    echo "<td>Resolved</td>";
                                                }

                                                if ($reports_row[4] == 1)
                                                {
                                                    echo "<td>Active</td>";
                                                }
                                                echo "<td><a href='report_details.php?report_id=$reports_row[0]'><button class='btn btn-primary'>Details</button></a></td>";
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
<?php

