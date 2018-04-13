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
				$stmt = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`, `location`,`contact_info`,`duration`,`IS_VOLUNTARY`,`tutor_id`,`owner_id`) VALUES (NULL,?,?,?,?,?,?,?,?);");
				
				$stmt->bind_param("ssssdsii",$subject,$time,$location,$contact_info,$duration,$voluntary,$user_id,$user_id);
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
				$stmt = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`, `location`,`contact_info`,`duration`,`IS_VOLUNTARY`,`tutee_id`,`owner_id`) VALUES (NULL,?,?,?,?,?,?,?,?);");
				
				$stmt->bind_param("ssssdsii",$subject,$time,$location,$contact_info,$duration,$voluntary,$user_id,$user_id);
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
	 public function get_balance($user_id){
			$stmt = $this->con->prepare("SELECT COALESCE(SUM(duration),0) FROM sessions WHERE tutor_id =? AND IS_FINISHED=1;");
			$stmt->bind_param("i",$user_id);
			$stmt->execute();
			$stmt->store_result(); 
			$stmt->bind_result($tutor_duration);
						$stmt->fetch();
			$stmt1 = $this->con->prepare("SELECT COALESCE(SUM(duration),0) FROM sessions WHERE tutee_id =? AND IS_FINISHED=1;");
			$stmt1->bind_param("i",$user_id);
			$stmt1->execute();
			$stmt1->store_result(); 

			$stmt1->bind_result($tutee_duration);
						$stmt1->fetch();
			$ret=$tutor_duration-$tutee_duration; 
			$res['balance'] = $ret; 
			return $res;
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
		public function createFeedback($rating, $feedback, $user_id,$username,$commentee,$email){
				$stmt1 = $this->con->prepare("SELECT user_id FROM user WHERE UserName=?;");
			    $stmt1->bind_param("s", $commentee);
			    $stmt1->execute(); 
			    $stmt1->store_result(); 
			    $stmt1->bind_result($id);
			    $row1=$stmt1->fetch();
				$stmt = $this->con->prepare("INSERT INTO `feedback` (`feedback_id`, `rating`, `feedback`, `user_id`,`username`,`email`) VALUES (NULL, ?, ?, ?, ?,?);");
				$stmt->bind_param("dsiss",$rating,$feedback,$id,$username,$email);
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
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutor_id = ? AND  tutee_id=0 AND IS_FINISHED=false AND IS_CANCELED=false; ");
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
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutor_id = ? AND  tutee_id!=0 AND IS_FINISHED=false AND IS_CANCELED=false; ");
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
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutor_id = ? AND  tutee_id!=0 AND IS_FINISHED=true AND IS_CANCELED=false; ");
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
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutee_id = ? AND  tutor_id=0 AND IS_FINISHED=false AND IS_CANCELED=false; ");
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
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutee_id = ? AND  tutor_id!=0 AND IS_FINISHED=false AND IS_CANCELED=false; ");
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
			$stmt = $this->con->prepare("SELECT subject,id FROM sessions WHERE tutee_id = ? AND  tutor_id!=0 AND IS_FINISHED=true AND IS_CANCELED=false; ");
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
			$stmt = $this->con->prepare("SELECT UserName, time, location, contact_info, tutor_id,duration,IS_VOLUNTARY FROM sessions, user WHERE id=? AND (sessions.tutor_id=user.user_id OR sessions.tutee_id=user.user_id); ");
			$stmt->bind_param("i", $session_id);
			$stmt->execute(); 
			$stmt->store_result(); 
            $stmt->bind_result($UserName,$time,$location,$contact_info,$tutor_id,$duration,$IS_VOLUNTARY);
			$res = array();
    		while($stmt->fetch()){
     				$res['time'] = $time; 
     				$res['location'] = $location;
    			 	$res['contact_info'] = $contact_info;
     				$res['username'] = $UserName;
     				$res['duration'] = $duration;
     				$res['IS_VOLUNTARY'] = $IS_VOLUNTARY;
    		}
    		return $res;
		}
           

                public function searchsession($subject){
                        $stmt = $this->con->prepare("SELECT sessions.id,subject,time,location,contact_info,duration,IS_VOLUNTARY,user.UserName, tutee_id FROM user,sessions WHERE user.user_id=sessions.tutee_id AND sessions.tutor_id=0 AND sessions.subject LIKE '%a%' ;");
                        $stmt->bind_param("s",$subject);
                        $stmt->execute();
                        $stmt->store_result();
                        $stmt->bind_result($session_id,$subject,$time,$location,$contact_info,$duration,$is_voluntary,$UserName,$tutee_id);
                        while($row=$stmt->fetch()){
                        $stmt2=$this->con->prepare(" SELECT AVG(rating) FROM feedback WHERE user_id=? ;");
                        $stmt2->bind_param("i",$tutee_id);
                        $stmt2->execute();
                        $stmt2->store_result();
                        $stmt2->bind_result($rating);
                        $row2=$stmt2->fetch();
 						$response[] = [
 								"session_id"=>$session_id,
       							 "subject" => $subject,
       							 "time" => $time,
        						 "location" => $location,
        						 "contact_info" => $contact_info,
							 "duration"=>$duration,
							 "IS_VOLUNTARY"=>$is_voluntary,
        						 "UserName" => $UserName,
        						 "rating" => $rating,
   							 ]; //assign each data to response array
                        }
                        return $response;
                } 
		public function tuteeSearch($subject){
                        $stmt = $this->con->prepare("SELECT sessions.id,subject,time,location,contact_info,duration,IS_VOLUNTARY,user.UserName,tutor_id FROM user,sessions WHERE user.user_id=sessions.tutor_id AND sessions.tutee_id=0 AND sessions.subject LIKE '%a%';");
                        $stmt->bind_param("s",$subject);
                        $stmt->execute();
                        $stmt->store_result();
                        $stmt->bind_result($session_id,$subject,$time,$location,$contact_info,$duration,$is_voluntary,$UserName,$tutor_id);
                       while($row=$stmt->fetch()){
                        $stmt2=$this->con->prepare(" SELECT AVG(rating) FROM feedback WHERE user_id=? ;");
                        $stmt2->bind_param("i",$tutor_id);
                        $stmt2->execute();
                        $stmt2->store_result();
                        $stmt2->bind_result($rating);
                        $row2=$stmt2->fetch();
 						$response[] = [
 								"session_id"=>$session_id,
       							 "subject" => $subject,
       							 "time" => $time,
        						 "location" => $location,
        						 "contact_info" => $contact_info,
							     "duration"=>$duration,
							     "IS_VOLUNTARY"=>$is_voluntary,
        						 "UserName" => $UserName,
        						 "rating" => $rating,
   							 ]; //assign each data to response array
                        }
                        return $response;
                }
		public function inviteSession($invitor_id,$guest,$session_id){
		   $stmt = $this->con->prepare("SELECT count(user_id), user_id FROM `user` WHERE UserName=?;");
		   $stmt->bind_param("s",$guest);
		   $stmt->execute();
		   $invite = array();
		   $stmt->bind_result($num,$invited_id);
		   while($stmt->fetch()){
		   	if($num==0){
		   		return 2;
		   	}
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
		public function cancel($user_id,$session_id){
		  $stmt1 = $this->con->prepare("SELECT owner_id,tutee_id,tutor_id FROM sessions WHERE id=?;");	
		  			    $stmt1->bind_param("i",$session_id);
		  			    $stmt1->execute();

		  	$stmt1->store_result(); 
            $stmt1->bind_result($owner_id,$tutee_id,$tutor_id);
    		$stmt1->fetch();
    		if($owner_id == $user_id){
		       $stmt = $this->con->prepare("UPDATE `sessions` SET IS_CANCELED=1 WHERE owner_id=? AND id=?;");			
			    $stmt->bind_param("ii",$user_id,$session_id);
		        if($stmt->execute()){
			    return 1;
		       }
		       else{
			      return 2;
		       }
    		}
    		else if($owner_id == $tutee_id){
		        $stmt = $this->con->prepare("UPDATE `sessions` SET tutor_id=0 WHERE id=?;");			
		        $stmt->bind_param("i",$session_id);
		        if($stmt->execute()){
			      return 1;
		        }
		        else{
			     return 2;
		       }
    		}
    		else if($owner_id == $tutor_id){
		        $stmt = $this->con->prepare("UPDATE `sessions` SET tutee_id=0 WHERE id=?;");			
		        $stmt->bind_param("i",$session_id);
		        if($stmt->execute()){
			      return 1;
		        }
		        else{
			     return 2;
		       }
    		}
    		else{
    			return 2;
    		}
		}
		public function get_invitation($user_id){
              $stmt = $this->con->prepare("SELECT session_id FROM invitation WHERE invited_id=? AND IS_ACCEPTED=0;");
                        $stmt->bind_param("i",$user_id);
                        $stmt->execute();
                        $stmt->store_result();
                        $stmt->bind_result($session_id);
                       while($row=$stmt->fetch()){
                        $stmt2=$this->con->prepare(" SELECT subject FROM sessions WHERE id=? ;");
                        $stmt2->bind_param("i",$session_id);
                        $stmt2->execute();
                        $stmt2->store_result();
                        $stmt2->bind_result($subject);
                        $row2=$stmt2->fetch();
                        $response[] =  $subject; //assign each data to response array
                        $response[] =  $session_id; //assign each data to response array
                        }
                        return $response;
                }
        public function acceptInvitation($session_id,$user_id){
		   $stmt = $this->con->prepare("SELECT invitor_id FROM invitation WHERE session_id=? AND invited_id=?;");
		   $stmt->bind_param("ii",$session_id,$user_id);
		   $stmt->execute();
		   $stmt->store_result();
		   $stmt->bind_result($invitor_id);
		   $stmt->fetch();
		   $stmt1 = $this->con->prepare("SELECT * FROM sessions WHERE id=?;");
		   $stmt1->bind_param("i",$session_id);
		   $stmt1->execute();
		                           $stmt1->store_result();
		   $stmt1->bind_result($id,$subject,$time,$duration,$location,$contact_info,$owner_id,$tutor_id,$tutee_id,$IS_VOLUNTARY,$IS_FINISHED,$IS_CANCELED);
		   $stmt1->fetch();
		   if($tutor_id == $invitor_id){
		   		     echo "6";
		     $stmt2 = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`,`duration`, `location`,`contact_info`,`owner_id`,`tutor_id`,`tutee_id`,`IS_VOLUNTARY`,`IS_FINISHED`,`IS_CANCELED`) VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?);");
		     $stmt2->bind_param("ssdssiiiiii",$subject,$time,$duration,$location,$contact_info,$owner_id,$user_id,$tutee_id,$IS_VOLUNTARY,$IS_FINISHED,$IS_CANCELED);
		     echo "4";
		   if($stmt2->execute()){
		   			   $stmt3 = $this->con->prepare("UPDATE `invitation` SET IS_ACCEPTED=1 WHERE session_id=? AND invited_id=?;");
		               $stmt3->bind_param("ii",$session_id,$user_id);
		               $stmt3->execute();
			return 1;
		   }
		   else{
			return 2;
		   }
		  }
		  else if($tutee_id == $invitor_id){
		     $stmt2 = $this->con->prepare("INSERT INTO `sessions` (`id`, `subject`, `time`,`duration`, `location`,`contact_info`,`owner_id`,`tutor_id`,`tutee_id`,`IS_VOLUNTARY`,`IS_FINISHED`,`IS_CANCELED`) VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?);");
		     $stmt2->bind_param("ssdssiiisss",$subject,$time,$duration,$location,$contact_info,$owner_id,$tutor_id,$user_id,$IS_VOLUNTARY,$IS_FINISHED,$IS_CANCELED);
		   if($stmt2->execute()){
		   			   $stmt3 = $this->con->prepare("UPDATE `invitation` SET IS_ACCEPTED=1 WHERE session_id=? AND invited_id=?;");
		               $stmt3->bind_param("ii",$session_id,$user_id);
		               $stmt3->execute();
			return 1;
		   }
		   else{
			return 2;
		   }
		  }

		} 

	}