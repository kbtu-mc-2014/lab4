<?php 
     require 'connect.php';
    $response = array();
        if(isset($_POST['id']) && isset($_POST['full_name'])) {
            $id = $_POST['id'];
            $full_name = $_POST['full_name'];
            $db = new DB_CONNECT();
            $result = mysql_query("UPDATE products SET full_name = '$full_name' WHERE id = $id");
            if($result) {
                $response["success"] = 1;
                $response["message"] = "Product successfully updated.";
                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "No product found";
                echo json_encode($response);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Required field(s) is missing";
            echo json_encode($response);
        }

 ?>