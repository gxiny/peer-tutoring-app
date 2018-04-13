
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	
		isset($_POST['invitor_id'])and
		isset($_POST['guest'])and
		isset($_POST['session_id']))
		{
		//operate the data further 
		
		$db = new DbOperations(); 
		
		$result = $db->inviteSession( 	$_POST['invitor_id'],
						$_POST['guest'],
						$_POST['session_id']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Inviation sent";
		}else{
			$response['error'] = true; 
			$response['message'] = "User not exist";			
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

?>