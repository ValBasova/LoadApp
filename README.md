# LoadApp 

LoadApp is an Android Kotlin application to demonstrate an example of the DownloadManager system 
service usage to handle long running downloads, implementation of a custom-built button animated via
Android Property Animation, as well as screen animation with MotionLayout to enhance the user 
experience. Implementation of contextual messages using notifications with custom functionality 
allows the app to keep users informed when downloads complete.


## Project Overview
The app has been implemented with the following techniques:

-  2 types of contextual messages are displayed to the user: *toast* and *notification*, which is
   created and displayed in the status bar. A notification is sent once the download is complete.
   When a user clicks on the notification button, the user lands on detail activity and the 
   notification gets dismissed. In detail activity, the status of the download is displayed and
   animated via *MotionLayout* upon opening the activity.

- *Custom loading button* created by extending View class and assigning custom attribute to it. Text
  and background are drawn using *canvas*. Using *Android Property Animation* — the *ValueAnimator*,
  the app demonstrates how custom button properties are animated with horizontal progress and circle
  progress: the width of the button gets animated from left to right, text gets changed based on 
  different states of the button, and circle gets animated from 0 to 360 degrees. 

[The final look of the app here.](https://giphy.com/gifs/WS0Gie8GSeEYJTIDJE)
