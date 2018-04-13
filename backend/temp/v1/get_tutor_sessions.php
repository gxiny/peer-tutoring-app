
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['user_id']))
		{
		$db = new DbOperations(); 
		$notyet = $db->get_tutor_sessions_notyet( $_POST['user_id']);
				$todo = $db->get_tutor_sessions_todo( $_POST['user_id']);
						$finished = $db->get_tutor_sessions_finished( $_POST['user_id']);
		if(!isset($notyet)){
				$notyet[]="null";
						}
			if(!isset($todo)){
				$todo[]="null";
						}
				if(!isset($finished)){
				$finished[]="null";
						}
		$response['notyet'] =$notyet;
		$response['todo'] =$todo;
		$response['finished'] =$finished;	

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
