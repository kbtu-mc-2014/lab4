<?php 

	$mysqli = false;

	function connectDB() {
		global $mysqli;
		$mysqli = new mysqli("localhost", "root", "", "mc_lab");
		$mysqli->query("SET NAMES 'utf8'");
	}

	function closeDB() {
		global $mysqli;
		$mysqli->close();
	}

	function getStudents() {
		global $mysqli;
		connectDB();
		$result = $mysqli->query("SELECT * FROM `students`");
		closeDB();
		return resultSetToArray($result); 	
	}

	function addStudent($student) {
		global $mysqli;
		connectDB();
		$mysqli->query("INSERT INTO `students` (`id`, `full_name`) VALUES ('', '$student')");
		closeDB();
	}

	function resultSetToArray($result_set) {
		$array = array();
		while (($row = $result_set->fetch_assoc()) != false) $array[] = $row;
		return $array;
	}

 ?>