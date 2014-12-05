<?php 
	require 'connect.php';
	$response = array();
        if(isset($_POST['full_name'])) {
            $full_name = $_POST['full_name'];
            $db = new DB_CONNECT();
            $result = mysql_query("INSERT INTO products (full_name) VALUES ('$full_name')");
            if($result) {
                $response["success"] = 1;
                $response["message"] = "Product successfully created!";
                echo json_encode($result);
            } else {
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred.";
                echo json_encode($response);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Required field(s) is missing";
            echo json_encode($response);
        }

 ?>