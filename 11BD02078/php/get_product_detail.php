<?php 
	require 'connect.php';
	$response = array();
        $db = new DB_CONNECT();
        if(isset($_GET['id'])) {
        $id = $_GET['id'];
        $result = mysql_query("SELECT * FROM products WHERE id = $id");
        if(!empty($result)) {
            if(mysql_num_rows($result) > 0) {
                $result = mysql_fetch_array($result);
                $product = array();
                $product["id"] = $result["id"];
                $product["full_name"] = $result["full_name"];
                $response["success"] = 1;
                $response["product"] = array();
                array_push($response["product"], $product);
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
    }

 ?>