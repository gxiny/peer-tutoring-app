
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['session_id']))
		{
		//operate the data further 

		$db = new DbOperations(); 

		$result = $db->get_session_detail( 	$_POST['session_id']
								);
			$response= $result; 
	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);