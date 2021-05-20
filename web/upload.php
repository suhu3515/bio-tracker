<?php
if($_SERVER['REQUEST_METHOD']=='POST')
{
    require_once 'DbConnect.php';

    $response = array();
    $userId = $_POST['user_id'];
    $caption = $_POST['caption'];
    $image = $_POST['image'];
/*    $image_parts = explode(";base64,", $_POST['image']);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type = $image_type_aux[1];
    $image_base64 = base64_decode($image_parts[1]);*/
    $name = round(microtime(true) * 1000) . '.jpg';
    $filedest = dirname(__FILE__) . "/user_posts/" . $name;
    $path = "user_posts/" . $name;
    $actual_path = "http://192.168.0.103/biotracker/$path";

    $res_new_post = $conn->query("insert into community_post(user_id,caption,post_image) values ('$userId','$caption','$path')");
    if ($res_new_post)
    {
        file_put_contents($path, base64_decode($image));
        $response['error'] = false;
        $response['message'] = 'Posted successfully...';
    }
    else
    {
        $response['error'] = true;
        $response['message'] = 'Something went wrong...';
    }

    echo json_encode($response);
}