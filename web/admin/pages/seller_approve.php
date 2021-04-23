<?php

include_once '../../DbConnect.php';

$seller_id = "".htmlspecialchars($_GET['seller_id']);
$seller_status = 1;

$seller_update = "update seller set seller_status='$seller_status' where seller_id='$seller_id'";
$seller_res = $conn->query($seller_update);
if ($seller_res)
{
    echo "<script>alert('Seller approved successfully...')</script>";
    echo "<script>window.location='sellers.php'</script>";
}