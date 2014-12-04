<?php

    require 'Slim/Slim.php';
    require 'lib/connect.php';
    \Slim\Slim::registerAutoloader();
    $app = new \Slim\Slim();
    $app -> config('debug', true);

    $app -> post('/updateStudent', function() use ($app) {

        $response = array();
        if(isset($_POST['id']) && isset($_POST['full_name'])) {
            $id = $_POST['id'];
            $full_name = $_POST['full_name'];
            $db = new DB_CONNECT();
            $result = mysql_query("UPDATE students SET full_name = '$full_name' WHERE id = $id");
            if($result) {
                $response["success"] = 1;
                $response["message"] = "Student successfully updated.";
                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "No student found";
                echo json_encode($response);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Required field(s) is missing";
            echo json_encode($response);
        }

    });

    $app -> post('/deleteStudent', function() use ($app) {

        $response = array();
        if(isset($_POST['id'])) {
            $id = $_POST['id'];
            $db = new DB_CONNECT();
            $result = mysql_query("DELETE FROM students WHERE id = $id");
            if(mysql_affected_rows()>0) {
                $response["success"] = 1;
                $response["message"] = "Student successfully deleted!";
                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "No student found!";
                echo json_encode($response);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Required field(s) is missing";
            echo json_encode($response); 
        }

    });

    $app -> post('/createNewStudent', function() use ($app) {

        $response = array();
        if(isset($_POST['full_name'])) {
            $full_name = $_POST['full_name'];
            $db = new DB_CONNECT();
            $result = mysql_query("INSERT INTO students (full_name) VALUES ('$full_name')");
            if($result) {
                $response["success"] = 1;
                $response["message"] = "Student successfully created!";
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

    }); 

    $app -> get('/getAllStudents', function() use ($app) {

    	$response = array();
        $db = new DB_CONNECT();
        $result = mysql_query("SELECT * FROM students") or die(mysql_error());
        if(mysql_num_rows($result)>0) {
            $response["students"] = array();
            while($row = mysql_fetch_array($result)) {
                $student = array();
                $student["id"] = $row["id"];
                $student["full_name"] = $row["full_name"];
                array_push($response["students"], $student);
            }
            $response["success"] = 1;
            echo json_encode($response);
        } else {
            $response["success"] = 0;
            $response["message"] = "No student found!";
            echo json_encode($response);
        }

    });

    $app -> get('/getStudentDetail', function() use ($app) {

        $response = array();
        $db = new DB_CONNECT();
        if(isset($_GET['id'])) {
        $id = $_GET['id'];
        $result = mysql_query("SELECT * FROM students WHERE id = $id");
        if(!empty($result)) {
            if(mysql_num_rows($result) > 0) {
                $result = mysql_fetch_array($result);
                $student = array();
                $student["id"] = $result["id"];
                $student["full_name"] = $result["full_name"];
                $response["success"] = 1;
                $response["student"] = array();
                array_push($response["student"], $student);
                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "No student found";
                echo json_encode($response);
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Required field(s) is missing";
            echo json_encode($response);
        }
    }

    });

$app->run();
