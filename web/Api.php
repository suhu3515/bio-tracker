<?php

require_once 'DbConnect.php';
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
                        $stmt = $conn->prepare("SELECT user_id, user_name, user_dob, user_hname, user_place, user_pincode, user_dst, user_mobile, user_email from users where user_mobile=?" );
                        $stmt->bind_param("s",$user_mob);
                        $stmt->execute();
                        $stmt->bind_result($user_id, $user_name, $user_dob, $user_hname, $user_place, $user_pincode, $user_district, $user_mob, $user_mail);
                        $stmt->fetch();

                        $user = array
                        (
                            'user_id'=>$user_id,
                            'user_name'=>$user_name,
                            'user_dob'=>$user_dob,
                            'user_hname'=>$user_hname,
                            'user_place'=>$user_place,
                            'user_pin'=>$user_pincode,
                            'user_dst'=>$user_district,
                            'user_mobile'=>$user_mob,
                            'user_email'=>$user_mail
                        );

                        $stmt->close();

                        $response['error']= false;
                        $response['message'] = 'User registered Successfully';
                        $response['user'] = $user;
                    }
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
                        $temp['start_date'] = $row_stmt3[4];
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

?>  