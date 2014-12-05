<?php 

	class DB_CONNECT {

		function __construct() {
			$this->connect();
		}

		function __destruct() {
			$this->close();
		}

		function connect() {
			require 'config.php';
			$con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
			$db = mysql_select_db(DB_DATABASE) or die(mysql_error());
			//query("SET NAMES 'UTF8'");
			return $con;
		}

		function close() {
			mysql_close();
		}

	}

 ?>