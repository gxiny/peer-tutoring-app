
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['rating']) and 
			isset($_POST['feedback']) and 
				isset($_POST['user_id']) and 
					isset($_POST['username'])and 
						isset($_POST['email']))
		{
		//operate the data further 

		$db = new DbOperations(); 

		$result = $db->createFeedback( 	$_POST['rating'],
										$_POST['feedback'],
										$_POST['user_id'],
										$_POST['username'],
										$_POST['email']
								);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Feedback created";
		}else{
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}
	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
