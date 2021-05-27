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

					<li class="sidebar-item">
						<a class="sidebar-link" href="reports.php">
              <i class="align-middle" data-feather="file-text"></i> <span class="align-middle">Reports</span>
            </a>
					</li>

                    <li class="sidebar-item active">
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

            <?php

            include_once '../../DbConnect.php';

            $tutorial = $_GET['tut_id'];

            $ins_sel = "select * from tutorials where tut_id='$tutorial'";
            $ins_res = $conn->query($ins_sel);
            $row_res = $ins_res->fetch_array();

            $ins_name = $row_res[2];
            $ins_text = $row_res[3];
            $ins_link = $row_res[4];

            ?>

			<main class="content">
				<div class="container-fluid p-0">
					<div class="row">
						<div class="col-6 col-xl-6">
							<div class="card">
                                <form action="" method="post" enctype="multipart/form-data">
                                    <br>
                                    <div class="form-group col-md-8">
                                        <label for="ins_name">Title</label>
                                        <?php
                                        echo "<input type='text' class='form-control' id='ins_name' name='ins_name' value='$ins_name' required>";
                                        ?>
                                    </div>
                                    <div class="form-group col-md-8">
                                        <br><label>Language</label>
                                        <br>
                                        <input type="radio" id="english" name="ins_language" value="English" checked>
                                        <label for="english">English</label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" id="malayalam" name="ins_language" value="Malayalam">
                                        <label for="malayalam">Malayalam</label>
                                    </div>
                                    <div class="form-group col-md-8">
                                        <br>
                                        <label for="ins_link">Social Link</label>
                                        <?php
                                        echo "<input type='url' class='form-control' id='ins_link' name='ins_link' value='$ins_link'>";
                                        ?>
                                    </div>
                                    <div class="form-group col-md-8">
                                        <br>
                                        <label for="ins_text">Tutorial</label>
                                        <?php
                                        echo "<textarea class='form-control' rows='4' id='ins_text' name='ins_text' required>$ins_text</textarea>";
                                        ?>
                                    </div>
                                    <div class="form-group col-md-8">
                                        <label>Tutorial Status </label>
                                        <br><label for="active">Active</label>
                                        <input type="checkbox" id="active" name="ins_status" value="active" checked>
                                    </div>
                                    <div class="row col-md-12">
                                        <div class="form-group col-md-6">
                                            <button type="submit" name="upd_tut" id="upd_tut" class="btn btn-primary">Update tutorial</button>
                                        </div>
                                </form>
                                <div class="form-group col-md-6">
                                    <a href="tutorials.php" style="color:red;">Cancel</a>
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

if (isset($_POST['upd_tut']))
{
    $instruction_title = $_POST['ins_name'];
    $instruction_text = $_POST['ins_text'];
    $instruction_link = $_POST['ins_link'];
    $instruction_lang = $_POST['ins_language'];
    $instruction_status = $_POST['ins_status'];
    if ($instruction_status=="active")
    {
        $status = 1;
    }
    else
    {
        $status = 0;
    }

    $res_ins = $conn->query("update tutorials set tut_language='$instruction_lang',tut_name='$instruction_title',tut_txt='$instruction_text',tut_link='$instruction_link',status='$status' where tut_id='$tutorial'");
    if ($res_ins)
    {
        echo "<script>alert('Updated tutorial')</script>";
        echo "<script>window.location='tutorials.php'</script>";
    }
}