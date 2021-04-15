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