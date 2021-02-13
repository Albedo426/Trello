<?php
	try {
	     $db = new PDO("mysql:host=localhost;dbname=ysproject", "root", "");
	     $db->query("SET CHARACTER SET utf8");
	} catch ( PDOException $e ){
	     print $e->getMessage();
	}
?>