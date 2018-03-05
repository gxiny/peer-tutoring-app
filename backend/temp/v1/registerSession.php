
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['subject']) and 
			isset($_POST['time']) and 
				isset($_POST['location']) and
						isset($_POST['contact_info'])and
							isset($_POST['user_id']))
		{
		//operate the data further 

		$db = new DbOperations(); 

		$result = $db->createSession( 	$_POST['subject'],
									$_POST['time'],
									$_POST['location'],
									$_POST['contact_info'],
									$_POST['user_id']
								);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Session created";
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
