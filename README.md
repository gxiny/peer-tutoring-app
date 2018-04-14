Peer Tutoring Mobile App
===========================================
|Author|Novice_Programmer|
|---|---
|Gitlab Link|git@gitlab.oit.duke.edu:xg41/ece651_novice_programmer.git

<center>![Peer Tutoring](https://i.imgur.com/C6eQkiC.png)</center>


# Contents
* [Installation Configuration](#installation-configuration)
* [Support Manual](#support-manual)
* [Code Structure](#code-structure)
* [Key API](#key-api)
* [Additional Files](#additional-files)
* [Test](#test)

## Installation Configuration
1. Pull down the frontend file from this repository
2. Run the frontend code on a virtual Andriod machine in the Android Studio  
3. The Andriod machine's API must later than API 26.
4. No need for backend configuration, since we have built the complete server for customers

## Support Manual
### Below are some tips when using our app.
### 1.  register
### Note that the password should be longer than 8 characters. Also, the password must contain at least one symbol, one number and one upper letter. If the password does not meet the limits, the registration will fail.
<img src="https://i.imgur.com/U41GuLN.png" width="360" height="640" alt="password should be longer than 8 characters" align=center />
<img src="https://i.imgur.com/s0Pe2pT.png" width="360" height="640" alt="password should contain at least one symbol" align=center />
<img src="https://i.imgur.com/a3UHhoA.png" width="360" height="640" alt="password should contain at least one number" align=center />
<img src="https://i.imgur.com/LcMKfv8.png" width="360" height="640" alt="password should contain at least one upper letter" align=center />
### 2. Session Creation
### When creating a session, instead of inputing a time by hand, users can choose time using the "select time" and "select date" button.
<img src="https://i.imgur.com/Erbfbl0.png" width="360" height="640" alt="session creation" align=center />
### 3. Search Session
### When searching a session, users should search by subject.
<img src="https://i.imgur.com/dxPnVIB.png" width="360" height="640" alt="search session" align=center />
### 4. Invite
### When inviting a person, this person must be a registered user. If not, the invitation will not be allowed.
<img src="https://i.imgur.com/63M0yGN.png" width="360" height="640" alt="invite" align=center />
### 5. Balance
### The balance actually shows the difference in values of tutor time(except voluntory time) and tutee time. This means balance = tutor time(except for voluntary time)-tutee time.
<img src="https://i.imgur.com/naCvzC9.png" width="360" height="640" alt="profile my invitation" align=center />


## Code Structure
![Code Structure](https://i.imgur.com/hdYQBtH.jpg)

## Key API
For backend, we use [LAMP](https://en.wikipedia.org/wiki/LAMP_(software_bundle)) in this project, which L stands for [Linux](https://en.wikipedia.org/wiki/Linux), A stands for [Apache](https://en.wikipedia.org/wiki/Apache_HTTP_Server), M stands for [MySQL](https://en.wikipedia.org/wiki/MySQL), and P stands for [PHP](https://en.wikipedia.org/wiki/PHP).
For frontend, we use [Android Studio](https://en.wikipedia.org/wiki/Android_Studio) to develop and emulate. 
![Key API](https://i.imgur.com/uMbkKv8.jpg)

## Additional Files
This is our [project plan](https://gitlab.oit.duke.edu/xg41/ece651_novice_programmer/blob/master/Project%20Plan.pdf).

This is our [user story](https://gitlab.oit.duke.edu/xg41/ece651_novice_programmer/blob/master/User_story.pdf).

## Test
This part identifies how we carried out Unit Test, Component Test and System Test as well as defines roles and responsibilities in testing schedule.

This is our [test plan](https://gitlab.oit.duke.edu/xg41/ece651_novice_programmer/blob/master/Test%20Plan.pdf).


