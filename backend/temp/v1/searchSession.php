<?php
require_once '../includes/DbOperations.php';

$response = array();


if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['subject'])){
      	$db = new DbOperations(); 
	$session=$db->searchsession($_POST['subject']); 
	if($session!=null){
            $response['error'] = false; 
            $response['result'] = $session;
          }else{
            $response['error'] = true; 
            $response['message'] = "No such session exits";          
        }

    }else{
        //$response['error'] = true; 
        //$response['message'] = "Required fields are missing";
	//$response['error'] = false;
    }
}
echo json_encode($response);
