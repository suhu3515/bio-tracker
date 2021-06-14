<?php

require_once 'DbConnect.php';
require_once dirname(__FILE__) . '/FileHandler.php';
$response = array();

if(isset($_GET['apicall']))
{
    switch($_GET['apicall'])
    {
        case 'user_register':

            if(isTheseParametersAvailable(array('fullName', 'dateOfBirth', 'mobileNumber','emailAddress','password','houseName','place','pinCode','district')))
            {
                $user_name = $_POST['fullName'];
                $user_dob = $_POST['dateOfBirth'];
                $user_mob = $_POST['mobileNumber'];
                $user_mail = $_POST['emailAddress'];
                $user_password = $_POST['password'];
                $user_hname = $_POST['houseName'];
                $user_place = $_POST['place'];
                $user_pincode = $_POST['pinCode'];
                $user_district = $_POST['district'];
                $role = 'USER';

                $stmt = $conn->prepare("SELECT * FROM login where mobile=?");
                $stmt->bind_param("s", $user_mob);
                $stmt->execute();
                $stmt->store_result();

                if($stmt->num_rows>0)
                {
                    $response['error'] = true;
                    $response['message'] = 'User Already Registered';
                    $stmt->close();
                }
                else
                {
                    $stmt = $conn->prepare("INSERT INTO users(user_name,user_dob,user_hname,user_place,user_pincode,user_dst,user_mobile,user_email) VALUES (?,?,?,?,?,?,?,?)");
                    $stmt->bind_param("ssssssss",$user_name,$user_dob,$user_hname,$user_place,$user_pincode,$user_district,$user_mob,$user_mail);
                    $stmt1 = $conn->prepare("INSERT INTO login(mobile,password,role) VALUES (?,?,?)");
                    $stmt1->bind_param("sss",$user_mob,$user_password,$role);

                    if ($stmt->execute() && $stmt1->execute() )
                    {
                        $response['error']= false;
                        $response['message'] = 'User registered Successfully';
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';

            }
        break;

        case "user_login":
            if (isTheseParametersAvailable(array('mobile','password'))) {
                $mobile = $_POST['mobile'];
                $password = $_POST['password'];
                $stmt13 = $conn->prepare("SELECT * from login where role='USER' and mobile=? and password=?");
                $stmt13->bind_param("ss", $mobile, $password);
                $stmt13->execute();
                $stmt13->store_result();
                if ($stmt13->num_rows > 0) {
                    $stmt14 = $conn->prepare("SELECT user_id, user_name, user_dob, user_hname, user_place, user_pincode, user_dst, user_mobile, user_email from users where user_mobile=?");
                    $stmt14->bind_param("s", $mobile);
                    $stmt14->execute();
                    $stmt14->bind_result($user_id, $user_name, $user_dob, $user_hname, $user_place, $user_pincode, $user_district, $user_mob, $user_mail);
                    $stmt14->fetch();

                    $new_date = date("d-m-Y", strtotime($user_dob));

                    $user = array
                    (
                        'user_id' => $user_id,
                        'user_name' => $user_name,
                        'user_dob' => $new_date,
                        'user_hname' => $user_hname,
                        'user_place' => $user_place,
                        'user_pin' => $user_pincode,
                        'user_dst' => $user_district,
                        'user_mobile' => $user_mob,
                        'user_email' => $user_mail
                    );

                    $stmt14->close();
                    $response['error'] = false;
                    $response['message'] = 'Logged in successfully';
                    $response['user'] = $user;
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Check username/password';
                    $stmt13->close();
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case 'add_farm':
            if(isTheseParametersAvailable(array('fish_type','fish_count','tank_vol','start_date','est_time','user_id')))
            {
                $fishName = $_POST['fish_type'];
                $fishCount = $_POST['fish_count'];
                $tankVolume = $_POST['tank_vol'];
                $startingDate = $_POST['start_date'];
                $estimatedTime = $_POST['est_time'];
                $userId = $_POST['user_id'];

                $stmt2 = $conn->prepare("insert into farm(fish_type, fish_count, tank_volume, start_date, est_time, user_id) values (?,?,?,?,?,?)");
                $stmt2->bind_param("ssssss", $fishName, $fishCount, $tankVolume, $startingDate, $estimatedTime, $userId);
                if($stmt2->execute())
                {
                    $stmt2->close();
                    $response['error']= false;
                    $response['message'] = 'Farm details added Successfully';

                }
                else
                {
                    $stmt2->close();
                    $response['error']= true;
                    $response['message'] = 'Something went wrong!';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';

            }
        break;

        case "view_farm_details":

            if (isTheseParametersAvailable(array('user_id')))
            {
                $farm = array();
                $farm_user_id = $_POST['user_id'];
                $stmt3 = "select * from farm where user_id='$farm_user_id'";
                $res_stmt3 = mysqli_query($conn,$stmt3);
                $temp = array();
                if ($res_stmt3)
                {
                    while ($row_stmt3 = mysqli_fetch_array($res_stmt3))
                    {
                        $temp['farm_id'] = $row_stmt3[0];
                        $temp['fish_type'] = $row_stmt3[1];
                        $temp['fish_count'] = $row_stmt3[2];
                        $temp['tank_volume'] = $row_stmt3[3];
                        $startDate = date("d-m-Y",strtotime($row_stmt3[4]));
                        $temp['start_date'] = $startDate;
                        $temp['est_time'] = $row_stmt3[5];
                        array_push($farm,$temp);
                    }
                    $response = $farm;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_products":

            $sel_products = "select product_id, product_name, product_price, product_qty, product_desc, product_img,seller_id from marketplace where product_status='1' and product_qty<>0 ";
            $res_products = $conn->query($sel_products);
            $products = array();
            $temp = array();
            if ($res_products)
            {
                while ($row_products = $res_products->fetch_array())
                {
                    $temp['product_id'] = $row_products[0];
                    $temp['product_name'] = $row_products[1];
                    $temp['product_price'] = $row_products[2];
                    $temp['product_qty'] = $row_products[3];
                    $temp['product_desc'] = $row_products[4];
                    $temp['product_img'] = $row_products[5];
                    $temp['seller_id'] = $row_products[6];
                    $sel_seller = "select seller_name from seller where seller_id='$row_products[6]'";
                    $res_seller = $conn->query($sel_seller);
                    $row_seller = $res_seller->fetch_array();
                    $temp['seller_name'] = $row_seller[0];
                    array_push($products,$temp);
                }
                $response = $products;
            }
        break;

        case "add_daily_data":

            if (isTheseParametersAvailable(array('ammonia_val','ph_val','oxygen_val','nitrogen_val','nitrate_val','nitrite_val','mortal_count','data_date','farm_id')))
            {
                $ammonia_value = $_POST['ammonia_val'];
                $ph_value = $_POST['ph_val'];
                $oxygen_value = $_POST['oxygen_val'];
                $nitrogen_value = $_POST['nitrogen_val'];
                $nitrate_value = $_POST['nitrate_val'];
                $nitrite_value = $_POST['nitrite_val'];
                $mortality_count = $_POST['mortal_count'];
                $data_date = $_POST['data_date'];
                $farm_id = $_POST['farm_id'];

                $stmt5= $conn->prepare("SELECT * FROM daily_data where farm_id=? and data_date=?");
                $stmt5->bind_param("ss",$farm_id, $data_date);
                $stmt5->execute();
                $stmt5->store_result();
                if ($stmt5->num_rows > 0)
                {
                    $stmt5->close();
                    $response['error'] = true;
                    $response['message'] = 'Data already added';
                }
                else
                {
                    $stmt6 = $conn->prepare("INSERT INTO daily_data(ammonia_level, ph_level, oxygen_level, nitrogen_level, nitrate_level, nitrite_level, mortality_count, data_date, farm_id) values (?,?,?,?,?,?,?,?,?)");
                    $stmt6->bind_param("sssssssss", $ammonia_value, $ph_value, $oxygen_value, $nitrogen_value, $nitrate_value, $nitrite_value,$mortality_count, $data_date, $farm_id);
                    if ($stmt6->execute())
                    {
                        $response['error']= false;
                        $response['message'] = 'Data added successfully...';
                    }
                    else
                    {;
                        $response['error']= true;
                        $response['message'] = 'Something went wrong!';
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_data_count":

            if(isTheseParametersAvailable(array('farm_id')))
            {
                $farm_id2 = $_POST['farm_id'];
                $stmt7 = "select * from daily_data where farm_id='$farm_id2'";
                $res_stmt7 = mysqli_query($conn, $stmt7);
                if ($res_stmt7)
                {
                    $response['count'] = mysqli_num_rows($res_stmt7);
                    $response['error'] = false;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_average":

            if (isTheseParametersAvailable(array('farm_id')))
            {
                $farm_id3 = $_POST['farm_id'];
                $stmt8 = "select avg(ammonia_level), avg(ph_level), avg(oxygen_level), avg(nitrogen_level), avg(nitrate_level), avg(nitrite_level), sum(mortality_count) from daily_data where farm_id='$farm_id3'";
                $res_stmt8 = mysqli_query($conn, $stmt8);
                if ($res_stmt8)
                {
                    $response['error'] = false;
                    $row_stmt8 = mysqli_fetch_array($res_stmt8);
                    $response['avg_ammonia'] = $row_stmt8[0];
                    $response['avg_ph'] = $row_stmt8[1];
                    $response['avg_oxygen'] = $row_stmt8[2];
                    $response['avg_nitrogen'] = $row_stmt8[3];
                    $response['avg_nitrate'] = $row_stmt8[4];
                    $response['avg_nitrite'] = $row_stmt8[5];
                    $response['sum_mortality'] = $row_stmt8[6];
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_dates":

            if (isTheseParametersAvailable(array('farm_id')))
            {
                $dates = array();
                $farm_id4 = $_POST['farm_id'];
                $stmt9 = "select data_date from daily_data where farm_id='$farm_id4'";
                $res_stmt9 = mysqli_query($conn, $stmt9);
                if ($stmt9)
                {
                    array_push($dates,"Average");
                    while ($row_stmt9 = mysqli_fetch_array($res_stmt9))
                    {
                        array_push($dates, $row_stmt9[0]);
                    }
                    $response = $dates;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_daily_data":
            if(isTheseParametersAvailable(array('farm_id','data_date')))
            {
                $farm_id5 = $_POST['farm_id'];
                $data_date1 = $_POST['data_date'];
                $stmt10 = "select ammonia_level, ph_level, oxygen_level, nitrogen_level, nitrate_level, nitrite_level, mortality_count from daily_data where farm_id='$farm_id5' and data_date='$data_date1'";
                $res_stmt10 = mysqli_query($conn, $stmt10);
                if ($res_stmt10)
                {
                    $response['error'] = false;
                    $row_stmt10 = mysqli_fetch_array($res_stmt10);
                    $response['ammonia'] = $row_stmt10[0];
                    $response['ph'] = $row_stmt10[1];
                    $response['oxygen'] = $row_stmt10[2];
                    $response['nitrogen'] = $row_stmt10[3];
                    $response['nitrate'] = $row_stmt10[4];
                    $response['nitrite'] = $row_stmt10[5];
                    $response['mortality'] = $row_stmt10[6];
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "Something went wrong!";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_seller":

            if (isTheseParametersAvailable(array('seller_id')))
            {
                $seller_id = $_POST['seller_id'];

                $seller_sel = "select seller_name, seller_place, seller_addr, seller_dst,seller_phone, seller_mail from seller where seller_id='$seller_id'";
                $seller_res = $conn->query($seller_sel);
                while ($seller_row = $seller_res->fetch_array())
                {
                    $response['seller_name'] = $seller_row[0];
                    $response['seller_place'] = $seller_row[1];
                    $response['seller_addr'] = $seller_row[2];
                    $response['seller_dst'] = $seller_row[3];
                    $response['seller_phone'] = $seller_row[4];
                    $response['seller_mail'] = $seller_row[5];
                    $response['error'] = false;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "add_orders":

            if(isTheseParametersAvailable(array('product_id','user_id','payment_mode','product_qty','order_amount','order_address')))
            {
                $product = $_POST['product_id'];
                $userId = $_POST['user_id'];
                $payment_mode = $_POST['payment_mode'];
                $product_qty = (int)$_POST['product_qty'];
                $order_amount = $_POST['order_amount'];
                $order_address = $_POST['order_address'];
                $order_date = date("d-m-Y");
                $est_date = date('d-m-Y', strtotime($order_date. ' + 10 days'));

                $res_order = $conn->query("insert into orders(product_id,product_qty,user_id,order_amount,order_date,payment_mode,order_address,delivery_date) values ('$product',$product_qty,'$userId','$order_amount','$order_date','$payment_mode','$order_address','$est_date')");
                if ($res_order)
                {
                   $res_prod = $conn->query("select product_qty from marketplace where product_id='$product'");
                   if($res_prod)
                   {
                       $row_prod = $res_prod->fetch_array();
                       $prod_qty = (int)$row_prod[0];
                       $stock = $prod_qty - $product_qty;
                       $upd_prod = $conn->query("update marketplace set product_qty='$stock' where product_id='$product'");
                       if ($upd_prod)
                       {
                           $response['error'] = false;
                           $response['message'] = 'order placed successfully...';
                       }
                       else
                       {
                           $response['error'] = true;
                           $response['message'] = 'Something went wrong!';
                       }
                   }
                   else
                   {
                       $response['error'] = true;
                       $response['message'] = 'Something went wrong!';
                   }
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong!';
                }

            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_orders":

            if (isTheseParametersAvailable(array('user_id')))
            {
                $userId2 = $_POST['user_id'];
                $order_res = $conn->query("select * from orders where user_id='$userId2'");
                $orders = array();
                $temp = array();
                if ($order_res)
                {
                    while ($order_row = $order_res->fetch_array())
                    {
                        $product_res = $conn->query("select product_name,product_img from marketplace where product_id = '$order_row[1]'");
                        $product_row = $product_res->fetch_array();
                        $temp['order_id'] = $order_row[0];
                        $temp['product_name'] = $product_row[0];
                        $temp['product_img'] = $product_row[1];
                        $temp['order_qty'] = $order_row[2];
                        $temp['order_amount'] = $order_row[4];
                        $temp['order_date'] = $order_row[8];
                        $temp['payment_mode'] = $order_row[6];
                        $temp['order_address'] = $order_row[5];
                        if ($order_row[9]==0)
                        {
                            $temp['pay_status'] = "Not paid";
                        }
                        if ($order_row[9]==1)
                        {
                            $temp['pay_status'] = "Paid";
                        }
                        if ($order_row[10]==0)
                        {
                            $temp['order_status'] = "Cancelled";
                        }
                        if ($order_row[10]==1)
                        {
                            $temp['order_status'] = "Rejected";
                        }
                        if ($order_row[10]==2)
                        {
                            $temp['order_status'] = "Confirmed";
                        }
                        if ($order_row[10]==3)
                        {
                            $temp['order_status'] = "Packed";
                        }
                        if ($order_row[10]==4)
                        {
                            $temp['order_status'] = "Dispatched";
                        }
                        if ($order_row[10]==5)
                        {
                            $temp['order_status'] = "Completed";
                        }
                        if ($order_row[7]==null)
                        {
                            $estDate = null;
                        }
                        else
                        {
                            $estDate = date("d-m-Y",strtotime($order_row[7]));
                        }
                        $temp['estimated_date'] = $estDate;
                        array_push($orders,$temp);
                    }
                    $response = $orders;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "cancel_order":

            if (isTheseParametersAvailable(array('order_id')))
            {
                $order_id = $_POST['order_id'];

                $cancel_order = $conn->query("update orders set order_status='0' where order_id='$order_id'");
                if ($cancel_order)
                {
                    $response['error'] = false;
                    $response['message'] = "Order cancelled successfully..";
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = "Something went wrong!";
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "add_new_post":

            if (isTheseParametersAvailable(array('posted_user','post_caption','date')))
            {
                $userId3 = $_POST['posted_user'];
                $caption = $_POST['post_caption'];
                $post_date = $_POST['date'];

                $res_new_text_post = $conn->query("insert into community_post(user_id,caption,post_date) values ('$userId3','$caption','$post_date')");
                if ($res_new_text_post)
                {
                    $response['error'] = false;
                    $response['message'] = 'Posted successfully...';
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong...';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "upload_image_post":

            if (isset($_POST['desc']) && isset($_POST['user_id']) && isset($_POST['date']) && strlen($_POST['desc'] > 0) && $_FILES['image']['error'] === UPLOAD_ERR_OK)
            {
                $upload = new FileHandler();

                $file = $_FILES['image']['tmp_name'];

                $user = $_POST['user_id'];

                $desc = $_POST['desc'];

                $date = $_POST['date'];

                if ($upload->saveFile($user,$file,getFileExtension($_FILES['image']['name']),$desc))
                {
                    $response['error'] = false;
                    $response['message'] = 'Posted Successfully';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

        break;

        case "get_posts":

            $posts_res = $conn->query("SELECT * FROM community_post where status='1' ORDER BY post_id DESC");
            $posts = array();
            $temp = array();
            while ($posts_row = $posts_res->fetch_array())
            {
                $temp['post_id'] = $posts_row[0];
                $temp['post_date'] = $posts_row[5];
                $temp['post_caption'] = $posts_row[2];
                $temp['post_image'] = $posts_row[3];
                $likes_count_res = $conn->query("select count(*) from post_likes where liked_post='$posts_row[0]'");
                $likes_count_row = $likes_count_res->fetch_array();
                $temp['post_likes'] = $likes_count_row[0];
                $comments_count_res = $conn->query("select count(*) from comments where post_id='$posts_row[0]'");
                $comments_count_row = $comments_count_res->fetch_array();
                $temp['post_comments'] = $comments_count_row[0];
                $user_res = $conn->query("select user_name from users where user_id='$posts_row[1]'");
                $user_row = $user_res->fetch_array();
                $temp['user_name'] = $user_row[0];
                array_push($posts,$temp);
            }
            $response = $posts;
        break;

        case "is_post_liked":

            if (isTheseParametersAvailable(array('post_id','user_id')))
            {
                $user = $_POST['user_id'];
                $post = $_POST['post_id'];
                $post_liked = $conn->query("select count(*) from post_likes where liked_user='$user' and liked_post='$post'");
                $post_liked_row = $post_liked->fetch_array();
                if ($post_liked_row[0]>0)
                {
                    $response['is_liked'] = true;
                    $response['error'] = false;
                }
                else if ($post_liked_row[0]==0)
                {
                    $response['is_liked'] = false;
                    $response['error'] = false;
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "like_post":

            if (isTheseParametersAvailable(array('user_id','post_id')))
            {
                $user = $_POST['user_id'];
                $post = $_POST['post_id'];
                $post_liked = $conn->query("select count(*) from post_likes where liked_user='$user' and liked_post='$post'");
                $post_liked_row = $post_liked->fetch_array();
                if ($post_liked_row[0]>0)
                {
                    $response['message'] = "you already liked this";
                }
                else
                {
                    $like_post_res = $conn->query("insert into post_likes(liked_user,liked_post) values ('$user','$post')");
                    if ($like_post_res)
                    {
                        $response['error'] = false;
                        $response['message'] = 'you liked this post';
                    }
                    else
                    {
                        $response['error'] = true;
                        $response['message'] = 'Something went wrong!';
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "dislike_post":
            if (isTheseParametersAvailable(array('user_id','post_id')))
            {
                $user = $_POST['user_id'];
                $post = $_POST['post_id'];
                $post_liked = $conn->query("select count(*) from post_likes where liked_user='$user' and liked_post='$post'");
                $post_liked_row = $post_liked->fetch_array();
                if ($post_liked_row[0]==0)
                {
                    $response['message'] = "you haven't liked this";
                }
                else
                {
                    $like_post_res = $conn->query("delete from post_likes where liked_user='$user' and liked_post='$post'");
                    if ($like_post_res)
                    {
                        $response['error'] = false;
                        $response['message'] = 'you disliked this post';
                    }
                    else
                    {
                        $response['error'] = true;
                        $response['message'] = 'Something went wrong!';
                    }
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
            break;

        case "add_comment":

            if (isTheseParametersAvailable(array('user_id','post_id','comment_text')))
            {
                $post = $_POST['post_id'];
                $user = $_POST['user_id'];
                $comment = $_POST['comment_text'];
                $comment_res = $conn->query("insert into comments(user_id,comm_text,post_id) values ('$user','$comment','$post')");
                if ($comment_res)
                {
                    $response['error'] = false;
                    $response['message'] = 'Comment added';
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong!';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_comments":

            if (isTheseParametersAvailable(array('post_id')))
            {
                $post = $_POST['post_id'];
                $stmt11 = $conn->query("select * from comments where post_id='$post'");
                $temp = array();
                $comments = array();
                while ($row_stmt11 = $stmt11->fetch_array())
                {
                    $temp['post_id'] = $row_stmt11[3];
                    $temp['comment_id'] = $row_stmt11[0];
                    $stmt12 = $conn->query("select user_name from users where user_id='$row_stmt11[1]'");
                    $row_stmt12 = $stmt12->fetch_array();
                    $temp['commented_user'] = $row_stmt12[0];
                    $temp['comment_text'] = $row_stmt11[2];
                    array_push($comments, $temp);
                }
                $response = $comments;
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "report_posts":

            if (isTheseParametersAvailable(array('user_id','report_text','post_id')))
            {
                $user = $_POST['user_id'];
                $report = $_POST['report_text'];
                $post = $_POST['post_id'];

                $res_stmt12 = $conn->query("insert into reports(user_id,report_text,post_id) values('$user','$report','$post') ");
                if ($res_stmt12)
                {
                    $response['error'] = false;
                    $response['message'] = 'Reported post successfully...';
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong!';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_instructions":

            if (isTheseParametersAvailable(array('language','title')))
            {
                $language = $_POST['language'];
                $title = $_POST['title'];

                $instr_res = $conn->query("select * from instructions where ins_language='$language' and ins_name='$title' and status='1'");
                while ($instr_row = $instr_res->fetch_array())
                {
                    $response['ins_id'] = $instr_row[0];
                    $response['message'] = $instr_row[1];
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_tutorials":

            $tut_res = $conn->query("select * from tutorials where status=1");
            $temp = array();
            $tutorials = array();
            while($tut_row = $tut_res->fetch_array())
            {
                $temp['tut_id'] = $tut_row[0];
                $temp['tut_title'] = $tut_row[2];
                $temp['tut_text'] = $tut_row[3];
                $temp['tut_link'] = $tut_row[4];
                array_push($tutorials,$temp);
            }
            $response = $tutorials;
        break;

        case "change_pass":

            if (isTheseParametersAvailable(array('student_mob','old_pass','new_pass')))
            {
                $mob = $_POST['student_mob'];
                $pass = $_POST['old_pass'];
                $new_pass = $_POST['new_pass'];

                $res_pass = $conn->query("select * from login where mobile='$mob' and password='$pass' and role='USER'");
                if ($res_pass->num_rows>0)
                {
                    $update_pass = $conn->query("update login set password='$new_pass' where mobile='$mob' and role='USER'");
                    if ($update_pass)
                    {
                        $response['error'] = false;
                        $response['message'] = 'Password changed successfully...';
                    }
                    else
                    {
                        $response['error'] = true;
                        $response['message'] = 'Something went wrong!';
                    }
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'Please check your current password';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "view_suggestion":

            if (isTheseParametersAvailable(array('language','topic')))
            {
                $language = $_POST['language'];
                $topic = $_POST['topic'];
                $res_stmt13 = $conn->query("select * from instructions where ins_language='$language' and ins_name='$topic' and status='1'");
                if ($res_stmt13)
                {
                    $row_stmt13 = $res_stmt13->fetch_array();
                    $response['error'] = false;
                    $response['message'] = $row_stmt13[3];
                }
                else
                {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong!';
                }
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;

        case "get_home_data":

            if(isTheseParametersAvailable(array('user_id')))
            {
                $user = $_POST['user_id'];
                $res_stmt14 = $conn->query("select count(*), sum(fish_count) from farm where user_id='$user'");
                while ($row_stmt14 = $res_stmt14->fetch_array())
                {
                    $response['farm_count'] = $row_stmt14[0];
                    $response['fish_total'] = $row_stmt14[1];
                }
                $res_stmt15 = $conn->query("select farm_id from farm where user_id='$user'");
                $count = 0;
                while ($row_stmt15 = $res_stmt15->fetch_array())
                {
                    $res_stmt16 = $conn->query("select sum(mortality_count) from daily_data where farm_id='$row_stmt15[0]'");
                    $row_stmt16 = $res_stmt16->fetch_array();
                    $count = $count + $row_stmt16[0];
                }
                $response['mortal_count'] = $count;
            }
            else
            {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
        break;
    }
}
else
{
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}

echo json_encode($response);

function isTheseParametersAvailable($params)
{
    foreach($params as $param)
    {
        if (!isset($_POST[$param]))
        {
            return false;
        }
    }

    return true;
}

function getFileExtension($file)
{
    $path_parts = pathinfo($file);
    return $path_parts['extension'];
}
?>  