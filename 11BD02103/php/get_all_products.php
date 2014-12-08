<?php 
	require 'connect.php';
	$response = array();
        $db = new DB_CONNECT();
        $result = mysql_query("SELECT * FROM products") or die(mysql_error());
        if(mysql_num_rows($result)>0) {
            $response["products"] = array();
            while($row = mysql_fetch_array($result)) {
                $product = array();
                $product["id"] = $row["id"];
                $product["full_name"] = $row["full_name"];
                array_push($response["products"], $product);
            }
            $response["success"] = 1;
            echo json_encode($response);
        } else {
            $response["success"] = 0;
            $response["message"] = "No product found!";
            echo json_encode($response);
        }

 ?>