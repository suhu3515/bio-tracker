<?php

class FileHandler
{

    private $con;

    public function __construct()
    {
        require_once dirname(__FILE__) . '/DatabaseConnect.php';

        $db = new DatabaseConnect();
        $this->con = $db->connect();
    }


    public function saveFile($user,$file, $extension, $desc)
    {
        $name = round(microtime(true) * 1000) . '.' . $extension;
        $filedest = dirname(__FILE__) . "/user_posts/" . $name;
        move_uploaded_file($file, $filedest);
        $mainFileDest = "user_posts/$name";


        $stmt = $this->con->prepare("INSERT INTO community_post (user_id, caption, post_image) VALUES (?, ?, ?)");
        $stmt->bind_param("sss", $user, $desc, $mainFileDest);
        if ($stmt->execute())
            return true;
        return false;
    }

}