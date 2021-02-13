<?php 
include 'connet_db.php';

	if(isset($_POST['Method'])){
		if($_POST['Method']=="getSCategories"){
			if(isset($_POST['CategoriesId'])){
				$id=$_POST['CategoriesId'];	
				$query = $db->query("SELECT * FROM categories,stories,users WHERE categories.storiesId=stories.storyId and categories.categoryResUId=users.userId and  categories.categoryId  = '{$id}'")->fetch(PDO::FETCH_ASSOC);
				if ( $query ){
				    $responsee["categories"]= array();
					$categories= array();
			        $categories['categoryId']=$query['categoryId'];
			        $categories['storyId']=$query['storiesId'];
			        $categories['categoryType']=$query['categoryType'];
			        $categories['FinishDate']=$query['FinishDate'];
			        $categories['categoryText']=$query['categoryText'];
			        $categories['categoryTitle']=$query['categoryTitle'];
			        $categories['categoryResUId']=$query['categoryResUId'];
			        array_push($responsee["categories"], $categories);
				}
				echo json_encode($responsee);			
			}else{
				//userid yok ise hepsini getir demek
				$query = $db->query("SELECT * FROM categories,stories,users WHERE categories.storiesId=stories.storyId and categories.categoryResUId=users.userId", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
				    $responsee["categories"]= array();
			        foreach( $query as $row ){
			        	$categories= array();
			        	$categories['categoryId']=$row['categoryId'];
				        $categories['storiesId']=$row['storiesId'];
				        $categories['categoryType']=$row['categoryType'];
				        $categories['FinishDate']=$row['FinishDate'];
				        $categories['categoryText']=$row['categoryText'];
				        $categories['categoryTitle']=$row['categoryTitle'];
				        $categories['categoryResUId']=$row['categoryResUId'];
				        array_push($responsee["categories"], $categories);
					}
					echo json_encode($responsee);	
				}
			}
		}else if($_POST['Method']=="getSCategoriescount"){
			if(isset($_POST['CategoriesId'])){
				$id=$_POST['CategoriesId'];	
				$query = $db->query("SELECT count(*) as 'count' FROM categories,stories,users WHERE categories.storiesId=stories.storyId and categories.categoryResUId=users.userId and  stories.storyId  =  '{$id}'  and categories.categoryType!=3 ")->fetch(PDO::FETCH_ASSOC);
				if ( $query ){
				    $retkon= array();
				    $retkon['control']=$query['count'];;
					echo json_encode($retkon);	
				}
			}
		}else if ($_POST['Method']=="getStory"){
			if(isset($_POST['StoryId'])){
				$id=$_POST['StoryId'];	
				$query = $db->query("SELECT * FROM stories,users WHERE  stories.storyCraUId =users.userId and  stories.storyId   = '{$id}'")->fetch(PDO::FETCH_ASSOC);
				if ( $query ){
				    $responsee["stories"]= array();
					$stories= array();
			        $stories['storyId']=$query['storyId'];
			        $stories['storyCraUId']=$query['storyCraUId'];
			        $stories['storyTitle']=$query['storyTitle'];
			        $stories['storyText']=$query['storyText'];
			        $stories['storyCraDate']=$query['storyCraDate'];
			        array_push($responsee["stories"], $stories);
				}
				echo json_encode($responsee);			
			}else{
				//userid yok ise hepsini getir demek
				$query = $db->query("SELECT * FROM stories,users WHERE  stories.storyCraUId =users.userId ", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
				    $responsee["stories"]= array();
			        foreach( $query as $row ){
			        	$stories= array();
				        $stories['storyId']=$row['storyId'];
				        $stories['storyCraUId']=$row['storyCraUId'];
				        $stories['storyTitle']=$row['storyTitle'];
				        $stories['storyText']=$row['storyText'];
				        $stories['storyCraDate']=$row['storyCraDate'];
					    array_push($responsee["stories"], $stories);
					}
					echo json_encode($responsee);	
				}
			}
		}else if ($_POST['Method']=="getUser"){
			if(isset($_POST['UserId'])){
				$id=$_POST['UserId'];	
				$query = $db->query("SELECT * FROM users WHERE userId = '{$id}'")->fetch(PDO::FETCH_ASSOC);
				if ( $query ){
				    $responsee["user"]= array();
					$user= array();
			        $user['userId']=$query['userId'];
			        $user['userMail']=$query['userMail'];
			        $user['userPass']=$query['userPass'];
			        array_push($responsee["user"], $user);
				}
				echo json_encode($responsee);			
			}else{
				//userid yok ise hepsini getir demek
				$query = $db->query("SELECT * FROM users", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
					$responsee["user"]= array();
			        foreach( $query as $row ){
			        	$user= array();
					    $user['userId']=$row['userId'];
					    $user['userMail']=$row['userMail'];
					    $user['userPass']=$row['userPass'];
					    array_push($responsee["user"], $user);
					}
					echo json_encode($responsee);	
				}
			}
		}
		else if ($_POST['Method']=="getUserLogin"){
			$userMail=$_POST['userMail'];	
			$userPass=$_POST['userPass'];	
			$query = $db->query("SELECT * FROM users WHERE userMail = '{$userMail}' and  userPass = '{$userPass}'")->fetch(PDO::FETCH_ASSOC);
			if ( $query ){
				$responsee["user"]= array();
				$user= array();
			    $user['userId']=$query['userId'];
			    $user['userMail']=$query['userMail'];
			    $user['userPass']=$query['userPass'];
			    array_push($responsee["user"], $user);
			}
			echo json_encode($responsee);			
		}else if ($_POST['Method']=="getStoryisEmail"){
			$userMail=$_POST['userMail'];	
			$query = $db->query("SELECT * FROM stories,users WHERE  stories.storyCraUId =users.userId and  users.userMail= '{$userMail}'", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
				    $responsee["stories"]= array();
			        foreach( $query as $row ){
			        	$stories= array();
				        $stories['storyId']=$row['storyId'];
				        $stories['storyCraUId']=$row['storyCraUId'];
				        $stories['storyTitle']=$row['storyTitle'];
				        $stories['storyText']=$row['storyText'];
				        $stories['storyCraDate']=$row['storyCraDate'];
					    array_push($responsee["stories"], $stories);
					}
					echo json_encode($responsee);	
				}
		}else if ($_POST['Method']=="getListKategoriesisTypeisMailisStoryId"){
			$type=$_POST['type'];	
			$userMail=$_POST['userMail'];	
			$storiesID=$_POST['storiesID'];	
			$query = $db->query("SELECT * FROM categories,stories,users WHERE categories.storiesId=stories.storyId and categories.categoryResUId=users.userId and categories.categoryType= '{$type}' and users.userMail = '{$userMail}' and categories.storiesId= '{$storiesID}'  ", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
				    $responsee["categories"]= array();
			        foreach( $query as $row ){
			        	$categories= array();
			        	$categories['categoryId']=$row['categoryId'];
				        $categories['storiesId']=$row['storiesId'];
				        $categories['categoryType']=$row['categoryType'];
				        $categories['FinishDate']=$row['FinishDate'];
				        $categories['categoryText']=$row['categoryText'];
				        $categories['categoryTitle']=$row['categoryTitle'];
				        $categories['categoryResUId']=$row['categoryResUId'];
				        array_push($responsee["categories"], $categories);
					}
					echo json_encode($responsee);	
				}
		}else if ($_POST['Method']=="getListKategoriesisMailisStoryId"){
			$userMail=$_POST['userMail'];	
			$storiesID=$_POST['storiesID'];	
			$query = $db->query("SELECT * FROM categories,stories,users WHERE categories.storiesId=stories.storyId and categories.categoryResUId=users.userId and users.userMail = '{$userMail}' and categories.storiesId= '{$storiesID}'  ", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
				    $responsee["categories"]= array();
			        foreach( $query as $row ){
			        	$categories= array();
			        	$categories['categoryId']=$row['categoryId'];
				        $categories['storiesId']=$row['storiesId'];
				        $categories['categoryType']=$row['categoryType'];
				        $categories['FinishDate']=$row['FinishDate'];
				        $categories['categoryText']=$row['categoryText'];
				        $categories['categoryTitle']=$row['categoryTitle'];
				        $categories['categoryResUId']=$row['categoryResUId'];
				        array_push($responsee["categories"], $categories);
					}
					echo json_encode($responsee);	
				}
		}
		else if ($_POST['Method']=="addCategory"){
			$storiesId=$_POST['storiesId'];	
			$categoryType=$_POST['categoryType'];	
			$FinishDate=$_POST['FinishDate'];	
			$categoryText=$_POST['categoryText'];	
			$categoryTitle=$_POST['categoryTitle'];	
			$categoryResUId=$_POST['categoryResUId'];	
			$query = $db->prepare("INSERT INTO categories SET
			storiesId = :storiesId,
			categoryType = :categoryType,
			FinishDate = :FinishDate,
			categoryText = :categoryText,
			categoryTitle = :categoryTitle,
			categoryResUId = :categoryResUId");
			$insert = $query->execute(array(
			      "storiesId" => $_POST['storiesId'],
			      "categoryType" => $_POST['categoryType'],
			      "FinishDate" => $_POST['FinishDate'],
			      "categoryText" => $_POST['categoryText'],
			      "categoryTitle" => $_POST['categoryTitle'],
			      "categoryResUId" => $_POST['categoryResUId'],
			));
			if ( $insert ){
			    $last_id = $db->lastInsertId();
			    $retkon= array();
			    $retkon['control']="1";
				echo json_encode($retkon);	
			}
		}
		else if ($_POST['Method']=="addStory"){
			$storyCraUId =$_POST['storyCraUId'];	
			$storyTitle=$_POST['storyTitle'];	
			$storyText=$_POST['storyText'];	
			$storyCraDate=$_POST['storyCraDate'];	
			$query = $db->prepare("INSERT INTO stories SET
			storyCraUId = :storyCraUId,
			storyTitle = :storyTitle,
			storyText = :storyText,
			storyCraDate = :storyCraDate");
			$insert = $query->execute(array(
			      "storyCraUId" => $_POST['storyCraUId'],
			      "storyTitle" => $_POST['storyTitle'],
			      "storyText" => $_POST['storyText'],
			      "storyCraDate" => $_POST['storyCraDate'],
			));
			if ( $insert ){
			    $last_id = $db->lastInsertId();
			    $retkon= array();
			    $retkon['control']="1";
				echo json_encode($retkon);	
			}
		}
		else if ($_POST['Method']=="nextOrProvius"){
			$location =$_POST['location'];
			$categoryId=$_POST['categoryId'];
			if($location=="+"){
				$query = $db->prepare("UPDATE categories SET categoryType = categoryType + 1  where categoryId=:categoryId");
				
			}else if($location=="-"){
				$query = $db->prepare("UPDATE categories SET categoryType = categoryType - 1  where categoryId=:categoryId");
			}
			
			$insert = $query->execute(array(
			      "categoryId" => $_POST['categoryId'],
			));
			if ( $insert ){
			    $last_id = $db->lastInsertId();
			    $retkon= array();
			    $retkon['control']="1";
				echo json_encode($retkon);	
			}
		}
		else if ($_POST['Method']=="getCategoryParser"){
			$storiesID =$_POST['storiesID'];
			$Type=$_POST['Type'];

			$query = $db->query("SELECT * FROM categories,stories,users WHERE categories.storiesId=stories.storyId and categories.categoryResUId=users.userId and categories.categoryType='{$Type}' and categories.storiesId='{$storiesID}'  ", PDO::FETCH_ASSOC);
				if ( $query->rowCount() ){
				    $responsee["categories"]= array();
			        foreach( $query as $row ){
			        	$categories= array();
			        	$categories['categoryId']=$row['categoryId'];
				        $categories['storiesId']=$row['storiesId'];
				        $categories['categoryType']=$row['categoryType'];
				        $categories['FinishDate']=$row['FinishDate'];
				        $categories['categoryText']=$row['categoryText'];
				        $categories['categoryTitle']=$row['categoryTitle'];
				        $categories['categoryResUId']=$row['categoryResUId'];
				        array_push($responsee["categories"], $categories);
					}
					echo json_encode($responsee);	
				}
		}

	}
?>	
 