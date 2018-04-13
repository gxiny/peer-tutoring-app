<center>Peer Tutoring Mobile App</center>
===========================================
|Author|Novice_Programmer|
|---|---
|Gitlab Link|git@gitlab.oit.duke.edu:xg41/ece651_novice_programmer.git

<center>![Peer Tutoring](https://i.imgur.com/C6eQkiC.png)</center>


# contents
* [Support Manual](#support-manual)
* [Installation/Configuration](#2)
* [Code Structure](#code-structure)
* [Key API](#key-api)
* [Additional Files](#additional-files)
* [Test](#test)

## Support Manual
For backend, we use [LAMP](https://en.wikipedia.org/wiki/LAMP_(software_bundle)) in this project, which L stands for [Linux](https://en.wikipedia.org/wiki/Linux), A stands for [Apache](https://en.wikipedia.org/wiki/Apache_HTTP_Server), M stands for [MySQL](https://en.wikipedia.org/wiki/MySQL), and P stands for [PHP](https://en.wikipedia.org/wiki/PHP).
For frontend, we use [Android Studio](https://en.wikipedia.org/wiki/Android_Studio) to develop and emulate. 

<h2 id="2">Installation/Configuration</h2>


<h2 id="3">Code Structure</h2>
![Code Structure](https://i.imgur.com/hdYQBtH.jpg)

<h2 id="4">Key API</h2>
![Key API](https://i.imgur.com/uMbkKv8.jpg)

<h2 id="5">Additional Files</h2>


## Test
This part identifies how we carried out Unit Test, Component Test and System Test as well as defines roles and responsibilities in testing schedule.

### 1. Unit test

Test Type | Test Object | Tester | Test Case | Test Result
:------------ | :------------- | :------------- | :------------- | :-------------
Unit Test | Login unit | Jiuyun Zhang; Junru Wang | pass different parameters to LoginRequest() to test the communication between android studio and database | passed
Unit Test | Login unit | Jiuyun Zhang; Junru Wang | pass different responses to onRsponse() for btnLogin in MainActivity to see whether it will handle error correctly | passed
Unit Test | Register unit | Jiuyun Zhang; Junru Wang | pass different parameters to RegisterRequest() to see if the user info will be saved to database | passed
Unit Test | Register unit | Jiuyun Zhang; Junru Wang | pass different responses to onRsponse() for btnRegister in RegisterActivity to see whether it will handle error correctly | passed
Unit Test | Register unit | Jiuyun Zhang; Junru Wang | Try to register with illegal password format to test the password check function | passed
Unit Test | Session Creation unit | Xinyu Zhou; Zu Liu; Xinyi Gong | In Add() function, pass different parameters to getParams() and see if the return value is correct or not. | passed
Unit Test | Session Search unit | Junru Wang;Xinyi Gong;Xinyu Zhou | Input different words to test if the onQueryTextChange() in both TutorSearch and TuteeSearch will work normally | passed
Unit Test | Session Search unit | Junru Wang;Xinyi Gong;Xinyu Zhou | Pass different responses to test if the onResponse() will handle error correctly | passed
Unit Test | Session Search unit | Junru Wang;Xinyi Gong;Xinyu Zhou | Pass different parameters to test if the TuteeSearchRequest() and TutorSearchRequest() will communicate with database correctly | passed
Unit Test | Appointment unit | Junru Wang;Xinyi Gong;Xinyu Zhou | Pass different responses to onResponse() for btnAppointment test if the Feedback() will work correctly  to register for a session | passed
Unit Test | Cancel Session unit | Jiuyun Zhang;Liu Zu | Pass different responses to onResponse() for btnCancel to test if it will work normally to cancel a session | passed
Unit Test | Rate unit | Jiuyun Zhang;Liu Zu | Pass different parameters to test if the Feedback() will communicate with database correctly | passed
Unit Test | invite unit | Xinyu Zhou | Try to invite users that exist and users taht does not exist to test if the invitation will cause an exception | passed


### 2. Component test

Test Type | Test Object | Tester | Test Case | Test Result
:------------ | :------------- | :------------- | :------------- | :-------------
Component Test | Login | Jiuyun Zhang; Junru Wang | Run in simulator and type in valid and invalid username and password to see if the login works correctly | passed
Component Test | Login | Jiuyun Zhang; Junru Wang | Run in several computers to login in at the same time to see if the app will crash | passed
Component Test | Register | Jiuyun Zhang; Junru Wang | Type in several same usernames and passwords to see if it can distinguish an existing account | passed
Component Test | Register | Jiuyun Zhang; Junru Wang | Run in simulator and type in invalid usernames and passwords | passed
Component Test | Session Creation | Xinyu Zhou; Zu Liu; Xinyi Gong | Run in simulator and use different types of input | passed
Component Test | Session Search | Junru Wang; Xinyu Zhou; Xinyi Gong | Tutee search sessions created by tutor using valid and invalid keywords | passed
Component Test | Session Search | Junru Wang; Xinyu Zhou; Xinyi Gong | Tutor search sessions created by tutee using valid and invalid keywords | passed
Component Test | Session Cancel | Jiuyu Zhang; Xinyu Zhou | Tutor cancel a session to see if the session will disappear in database | passed
Component Test | Session Cancel | Jiuyu Zhang; Xinyu Zhou  | Tutee cancel a session to see if the session will disappear in tutor's to do list and appear in his not yet list | passed
Component Test | Appointment | Junru Wang; Xinyu Zhou; Xinyi Gong | The tutee registers for an existing session which has not been registered | passed
Component Test | Appointment | Junru Wang; Xinyu Zhou; Xinyi Gong | The tutee registers for a session which has been registered | passed
Component Test | Appointment | Junru Wang; Xinyu Zhou; Xinyi Gong | Two or more tutees register for a session at the same time to see if there is any race consition | passed
Component Test | Rate | Jiuyu Zhang; Liu Zu | The tutor rates on his tutee and a tutee rates on his tutor and then check the database to see if the rate has been recorded | passed
Component Test | Peer Invite | Xinyu Zhou | The tutor invites another tutor and a tutee invites another tutee and then check the database to see if the invite component will work correctly | passed
Component Test | Peer Invite | Xinyu Zhou | The tutor/tutee check his invitations in his profile to see if his invitations will display correctly | passed


### 3. System test

Test Type | Test Object | Tester | Test Case | Test Result
:------------ | :------------- | :------------- | :------------- | :-------------
System Test | Peer Tutoring Mobile App version 3.0 | Jiuyun Zhang; Junru Wang; Xinyu Zhou; Zu Liu; Xinyi Gong | Run in different models of mobiles to see if the UI will display in normal ways | passed
System Test | Peer Tutoring Mobile App version 3.0 | Jiuyun Zhang; Junru Wang; Xinyu Zhou; Zu Liu; Xinyi Gong | Run the app using different languages to see if it can store and display different languages correctly | passed
System Test | Peer Tutoring Mobile App version 3.0 | Jiuyun Zhang; Junru Wang; Xinyu Zhou; Zu Liu; Xinyi Gong | Test app behavior when mobile receiving texts or phone calls | passed
System Test | Peer Tutoring Mobile App version 3.0 | Jiuyun Zhang; Junru Wang; Xinyu Zhou; Zu Liu; Xinyi Gong | Test app behavior when Test app behavior when there is no Internet connection | passed
System Test | Peer Tutoring Mobile App version 3.0 | Jiuyun Zhang; Junru Wang; Xinyu Zhou; Zu Liu; Xinyi Gong | All teammates keep using this app to register, login, create session and search to test the stability of the system | passed
System Test | Peer Tutoring Mobile App version 3.0 | Jiuyun Zhang; Junru Wang; Xinyu Zhou; Zu Liu; Xinyi Gong | Try to hack this app using common approaches such as buffer overflow, SQL injection etc., to test the safety of this system | passed

