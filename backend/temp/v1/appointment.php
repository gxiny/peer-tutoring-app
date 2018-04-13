
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['session_id']) and 
			isset($_POST['tutor_id']))
		{
		$db = new DbOperations(); 
		$result = $db->appointment( 	$_POST['session_id'],
										$_POST['tutor_id']
								);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "appointment  success";
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
