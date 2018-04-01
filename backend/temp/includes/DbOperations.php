<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createSession_test($subject, $time, $location,$contact_info,$user_id){
			//if($this->isUserExist($username,$email)){
			//	return 0; 
			//}

				//$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`, `location`,`contact_info`,`user_id`) VALUES (NULL, ?, ?, ?, ?,?);");
				$stmt->bind_param("ssssi",$subject,$time,$location,$contact_info,$user_id);
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
		}

			public function createSession($subject, $time, $location,$contact_info,$duration,$voluntary,$user_id){
			//if($this->isUserExist($username,$email)){
			//	return 0; 
			//}

				//$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`, `location`,`contact_info`,`duration`,`IS_VOLUNTARY`,`tutor_id`) VALUES (NULL,?,?,?,?,?,?,?);");
				
				$stmt->bind_param("ssssdsi",$subject,$time,$location,$contact_info,$duration,$voluntary,$user_id);
				if($stmt->execute()){
					$stmt = $this->con->prepare("SELECT MAX(id) FROM sessions;");
					$stmt->execute();
					$result = array();
					$stmt->bind_result($session_id);
					while($stmt->fetch()){
						$result['id']=$session_id;
					}
					return $session_id; 
				}else{
					return 0; 
				}
		} 
		public function tuteeCreate($subject, $time, $location,$contact_info,$duration,$voluntary,$user_id){
			//if($this->isUserExist($username,$email)){
			//	return 0; 
			//}

				//$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`, `location`,`contact_info`,`duration`,`IS_VOLUNTARY`,`tutee_id`) VALUES (NULL,?,?,?,?,?,?,?);");
				
				$stmt->bind_param("ssssdsi",$subject,$time,$location,$contact_info,$duration,$voluntary,$user_id);
				if($stmt->execute()){
					$stmt = $this->con->prepare("SELECT MAX(id) FROM sessions;");
					$stmt->execute();
					$result = array();
					$stmt->bind_result($session_id);
					while($stmt->fetch()){
						$result['id']=$session_id;
					}
					return $session_id; 
				}else{
					return 0; 
				}
		}

		public function createUser($UserName, $Password, $Email){
			if($this->isUserExist($UserName,$Email)){
				return 0; 
			}
			else{
				$pass = md5($Password);
				$stmt = $this->con->prepare("INSERT INTO `user` (`user_id`, `UserName`, `Password`, `Email`) VALUES (NULL, ?, ?, ?);");
				$stmt->bind_param("sss",$UserName,$pass,$Email);
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}


		public function userLogin($UserName, $Password){
			$pass = md5($Password);
			$stmt = $this->con->prepare("SELECT user_id FROM user WHERE UserName = ? AND Password = ?");
			$stmt->bind_param("ss",$UserName,$pass);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		public function getUserByUsername($UserName){
			$stmt = $this->con->prepare("SELECT * FROM user WHERE UserName = ?");
			$stmt->bind_param("s",$UserName);
			$stmt->execute();
			$stmt->store_result();
			$res = array();
			$stmt->bind_result($user_id,$UserName,$Password,$Email);
    		while($stmt->fetch()){
     				$res['user_id'] = $user_id; 
     				$res['UserName'] = $UserName;
    			 	//$res['Password'] = $Password;
     				$res['Email'] = $Email;
    		}
    		return $res;
		}

		private function isUserExist($UserName, $Email){
			$stmt = $this->con->prepare("SELECT user_id FROM user WHERE UserName = ? OR Email = ?");
			$stmt->bind_param("ss", $UserName, $Email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function createFeedback($rating, $feedback, $user_id,$username,$email){
				$stmt = $this->con->prepare("INSERT INTO `feedback` (`feedback_id`, `rating`, `feedback`, `user_id`,`username`,`email`) VALUES (NULL, ?, ?, ?, ?,?);");
				$stmt->bind_param("dsiss",$rating,$feedback,$user_id,$username,$email);
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
		}
		public function appointment($session_id, $tutor_id){
				$stmt = $this->con->prepare("UPDATE `sessions` SET tutor_id=? WHERE id=?;");
				$stmt->bind_param("ii",$tutor_id,$session_id);
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
		}
		public function tuteeAppoint($session_id, $tutee_id){
				$stmt = $this->con->prepare("UPDATE `sessions` SET tutee_id=? WHERE id=?;");
				$stmt->bind_param("ii",$tutee_id,$session_id);
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
		}
		public function finalize($session_id){
				$stmt = $this->con->prepare("UPDATE `sessions` SET IS_FINISHED=true WHERE id=?;");
				$stmt->bind_param("i",$session_id);
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
		}
		public function get_tutor_sessions_notyet($user_id){
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutor_id = ? AND  tutee_id=0 AND IS_FINISHED=false; ");
			$stmt->bind_param("i", $user_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($subject,$session_id);
                while($row=$stmt->fetch()){
 						$notyet[] = 
       						 $subject;
       					$notyet[] = 
       						 $session_id
   							 ; //assign each data to response array
                        }
			return $notyet; 
		}
				public function get_tutor_sessions_todo($user_id){
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutor_id = ? AND  tutee_id!=0 AND IS_FINISHED=false; ");
			$stmt->bind_param("i", $user_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($subject,$session_id);
                while($row=$stmt->fetch()){
 						$todo[] =  $subject   ; 
 						$todo[] =  $session_id   ;                        //assign each data to response array
                        }
			return $todo; 
		}
		public function get_tutor_sessions_finished($user_id){
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutor_id = ? AND  tutee_id!=0 AND IS_FINISHED=true; ");
			$stmt->bind_param("i", $user_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($subject,$session_id);
                while($row=$stmt->fetch()){
 						$finieshed[] =  $subject; //assign each data to response array
 						$finieshed[] =  $session_id;
                        }
			return $finieshed; 
		}
public function get_tutee_sessions_notyet($user_id){
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutee_id = ? AND  tutor_id=0 AND IS_FINISHED=false; ");
			$stmt->bind_param("i", $user_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($subject,$session_id);
                while($row=$stmt->fetch()){
 						$notyet[] = 
       						 $subject;
       					$notyet[] = 
       						 $session_id
   							 ; //assign each data to response array
                        }
			return $notyet; 
		}
				public function get_tutee_sessions_todo($user_id){
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutee_id = ? AND  tutor_id!=0 AND IS_FINISHED=false; ");
			$stmt->bind_param("i", $user_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($subject,$session_id);
                while($row=$stmt->fetch()){
 						$todo[] =  $subject   ; 
 						$todo[] =  $session_id   ;                        //assign each data to response array
                        }
			return $todo; 
		}
		public function get_tutee_sessions_finished($user_id){
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutee_id = ? AND  tutor_id!=0 AND IS_FINISHED=true; ");
			$stmt->bind_param("i", $user_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($subject,$session_id);
                while($row=$stmt->fetch()){
 						$finieshed[] =  $subject; //assign each data to response array
 						$finieshed[] =  $session_id;
                        }
			return $finieshed; 
		}
		public function get_session_detail($session_id){
			$stmt = $this->con->prepare("SELECT UserName, time, location, contact_info, tutor_id FROM sessions, user WHERE id=? AND (sessions.tutor_id=user.user_id OR sessions.tutee_id=user.user_id); ");
			$stmt->bind_param("i", $session_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($UserName,$time,$location,$contact_info,$tutor_id);
  
			$res = array();
    		while($stmt->fetch()){
     				$res['time'] = $time; 
     				$res['location'] = $location;
    			 	$res['contact_info'] = $contact_info;
     				$res['username'] = $UserName;
    		}
    		return $res;
		}
           

                public function searchsession($subject){
                        $stmt = $this->con->prepare("SELECT sessions.id,subject,time,location,contact_info,duration,IS_VOLUNTARY,user.UserName FROM user,sessions WHERE user.user_id=sessions.tutee_id AND sessions.tutor_id=0 AND sessions.subject= ? ;");
                        $stmt->bind_param("s",$subject);
                        $stmt->execute();
                        $stmt->store_result();
                        $stmt->bind_result($session_id,$subject,$time,$location,$contact_info,$duration,$is_voluntary,$UserName);
                        while($row=$stmt->fetch()){
 						$response[] = [
 								"session_id"=>$session_id,
       							 "subject" => $subject,
       							 "time" => $time,
        						 "location" => $location,
        						 "contact_info" => $contact_info,
							 "duration"=>$duration,
							 "IS_VOLUNTARY"=>$is_voluntary,
        						 "UserName" => $UserName,
   							 ]; //assign each data to response array
                        }
                        return $response;
                } 
		public function tuteeSearch($subject){
                        $stmt = $this->con->prepare("SELECT sessions.id,subject,time,location,contact_info,duration,IS_VOLUNTARY,user.UserName FROM user,sessions WHERE user.user_id=sessions.tutor_id AND sessions.tutee_id=0 AND sessions.subject= ? ;");
                        $stmt->bind_param("s",$subject);
                        $stmt->execute();
                        $stmt->store_result();
                        $stmt->bind_result($session_id,$subject,$time,$location,$contact_info,$duration,$is_voluntary,$UserName);
                        while($row=$stmt->fetch()){
 						$response[] = [
 								"session_id"=>$session_id,
       							 "subject" => $subject,
       							 "time" => $time,
        						 "location" => $location,
        						 "contact_info" => $contact_info,
							 "duration"=>$duration,
							 "IS_VOLUNTARY"=>$is_voluntary,
        						 "UserName" => $UserName,
   							 ]; //assign each data to response array
                        }
                        return $response;
                }
		public function inviteSession($invitor_id,$guest,$session_id){
		   $stmt = $this->con->prepare("SELECT user_id FROM `user` WHERE UserName=?;");
		   $stmt->bind_param("s",$guest);
		   $stmt->execute();
		   $invite = array();
		   $stmt->bind_result($invited_id);
		   while($stmt->fetch()){
			$invite['user_id']=$invited_id;
		   }
		   $stmt = $this->con->prepare("INSERT INTO `invitation` (`invitation_id`,`session_id`,`invited_id`,`IS_ACCEPTED`,`invitor_id`) VALUES (NULL,?,?,0,?);");
		   $stmt->bind_param("iii",$session_id,$invited_id,$invitor_id);
		   if($stmt->execute()){
			return 1;
		   }
		   else{
			return 2;
		   }
		}
	}