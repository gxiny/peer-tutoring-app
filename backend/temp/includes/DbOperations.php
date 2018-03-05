<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createSession($subject, $time, $location,$contact_info,$user_id){
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

	}